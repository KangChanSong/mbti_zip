package com.mbtizip.service.file;

import com.mbtizip.domain.file.File;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.file.FileRepository;
import com.mbtizip.repository.person.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    private final Path rootLocation = Paths.get("C:/temp_files");

    private final PersonRepository personRepository;
    private final FileRepository fileRepository;

    @Override
    public Boolean saveFile(Long personId, MultipartFile file) {
        Person findPerson = checkAndReturn(personId);
        String uuid = createUuid();
        storeInLocal(file, uuid);
        return saveInDb(file, findPerson , uuid);
    }

    @Override
    public InputStream loadFile(Long personId) {
        Person findPerson = checkAndReturn(personId);
        File findFile = fileRepository.findByPerson(findPerson);
        return loadFromLocal(findFile);
    }

    @Override
    public Boolean deleteFile(Long personId, String filename) {
        Person findPerson = personRepository.find(personId);
        if(findPerson == null) throw new IllegalArgumentException("Person 을 찾을 수 없습니다 : " + personId);
        deleteFormLocal(filename);
        fileRepository.deleteByPerson(findPerson);
        return true;
    }


    //== private 메서드 ==//

    private String createUuid(){
        return UUID.randomUUID().toString();
    }

    private void storeInLocal(MultipartFile file, String uuid) {

        try {
            Path destinationFile = this.rootLocation.resolve(file.getOriginalFilename())
                    .normalize().toAbsolutePath();

            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e){
            throw new RuntimeException("파일을 저장하는데 실패했습니다.", e);
        }

    }

    private Boolean saveInDb(MultipartFile file, Person person, String uuid) {
        File fileEntity = File.builder()
                .name(file.getOriginalFilename())
                .uuid(uuid)
                .build();

        fileEntity.setPerson(person);

        Long saveId = fileRepository.save(fileEntity);
        return saveId == null ? false : true;
    }

    private Person checkAndReturn(Long personId){
        Person findPerson = personRepository.find(personId);
        if(findPerson == null) throw new IllegalArgumentException("Person 을 찾을 수 없습니다 : " + personId);
        return findPerson;
    }


    private InputStream loadFromLocal(File file) {
        try {
            Path loaded = rootLocation.resolve(file.getUuid() + "_" + file.getName());

            Resource resource = new UrlResource(loaded.toUri());

            if(resource.exists() || resource.isReadable()){
                return new FileInputStream(resource.getFile());
            } else {
                throw new RuntimeException("파일을 읽을 수 없습니다.");
            }
        }
        catch (Exception e){
            throw new RuntimeException("파일을 읽을 수 없습니다.", e);
        }
    }


    private void deleteFormLocal(String filename) {
        this.rootLocation
                .resolve(filename)
                .toFile()
                .delete();

    }
}
