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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    private final Path rootLocation = Paths.get("C:/fileupload");

    private final PersonRepository personRepository;
    private final FileRepository fileRepository;

    @Transactional
    @Override
    public Boolean saveFile(Long personId, MultipartFile file) {
        Person findPerson = checkAndReturn(personId);
        String uuid = createUuid();
        storeInLocal(file, uuid);
        return saveInDb(file, findPerson , uuid);
    }

    @Override
    public byte[] loadFile(Long personId) {
        Person findPerson = checkAndReturn(personId);
        File findFile = fileRepository.findByPerson(findPerson);
        return loadFromLocal(findFile);
    }

    @Transactional
    @Override
    public Boolean deleteFile(Long personId) {
        Person findPerson = checkAndReturn(personId);
        File file = Optional.of(fileRepository.findByPerson(findPerson))
                .orElseThrow(() -> {throw new IllegalArgumentException("Person 에 해당하는 File을 찾을 수 없습니다. personId : " + personId);});

        boolean isDeleted = deleteFormLocal(file);

        if(isDeleted) {
            fileRepository.deleteByPerson(findPerson);
            return true;
        }
        else return false;
    }


    //== private 메서드 ==//

    private String createUuid(){
        return UUID.randomUUID().toString();
    }

    private void storeInLocal(MultipartFile file, String uuid) {

        try {
            Path destinationFile = this.rootLocation.resolve(uuid + "_" + file.getOriginalFilename())
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


    private byte[] loadFromLocal(File file) {
        try {
            Path loaded = rootLocation.resolve(file.getUuid() + "_" + file.getName());

            Resource resource = new UrlResource(loaded.toUri());

            if(resource.exists() || resource.isReadable()){
                return new FileInputStream(resource.getFile()).readAllBytes();
            } else {
                throw new RuntimeException("파일을 읽을 수 없습니다.");
            }
        }
        catch (Exception e){
            throw new RuntimeException("파일을 읽을 수 없습니다.", e);
        }
    }


    private boolean deleteFormLocal(File file) {

        String name = file.getName();
        String uuid = file.getUuid();

        return this.rootLocation
                .resolve(uuid+"_"+name)
                .toFile()
                .delete();

    }
}
