package com.mbtizip.service.file;

import com.mbtizip.domain.common.CommonEntity;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;
import com.mbtizip.exception.NoEntityFoundException;
import com.mbtizip.repository.file.FileRepository;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.person.PersonRepository;
import com.mbtizip.service.file.store.StoreService;
import com.mbtizip.util.ErrorMessageProvider;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.awt.image.ColorModel;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static com.mbtizip.util.ErrorMessageProvider.INVALID_INSTANCE;
import static com.mbtizip.util.ErrorMessageProvider.NO_ENTITY_FOUND;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{


    private final PersonRepository personRepository;
    private final JobRepository jobRepository;
    private final FileRepository fileRepository;
    private final StoreService storeService;

    @Transactional
    @Override
    public Boolean saveFileWithPerson(Long personId, MultipartFile file) {
        Person findPerson = checkAndReturn(Person.class, personId);
        return saveInDb(file, findPerson);
    }
    @Transactional
    @Override
    public Boolean saveFileWithJob(Long jobId, MultipartFile file) {
        Job findJob = checkAndReturn(Job.class,jobId);
        return saveInDb(file, findJob );
    }
    @Override
    public byte[] loadFileByPerson(Long personId) {
        Person findPerson = checkAndReturn(Person.class, personId);
        return storeService.loadFromLocal(fileRepository.findByPerson(findPerson));
    }
    @Override
    public byte[] loadFileByJob(Long jobId) {
        Job findJob = checkAndReturn(Job.class, jobId);
        return storeService.loadFromLocal(fileRepository.findByJob(findJob));
    }
    @Transactional
    @Override
    public Boolean deleteFileByPerson(Long personId) {
        return deleteFileByObject(Person.class, personId);
    }

    @Transactional
    @Override
    public Boolean deleteFileByJob(Long jobId) {
        return deleteFileByObject(Job.class, jobId);
    }

    //== private 메서드 ==//
    private Boolean saveInDb(MultipartFile file, Object obj) {
        String uuid = UUID.randomUUID().toString();
        storeService.storeInLocal(file, uuid);

        File fileEntity = File.builder()
                .name(file.getOriginalFilename())
                .uuid(uuid)
                .build();

        checkAndJoin(obj, fileEntity);

        Long saveId = fileRepository.save(fileEntity);
        return saveId == null ? false : true;
    }

    private Boolean deleteFileByObject(Class cls , Long id){
        Object found = checkAndReturn(cls, id);
        Optional<File> optFile;
        if(found instanceof Person) optFile = Optional.of(fileRepository.findByPerson((Person) found));
        else optFile = Optional.of(fileRepository.findByJob((Job) found));

        File file = optFile.orElseThrow(() -> {throw new IllegalArgumentException(NO_ENTITY_FOUND + " id : " + id);});
        boolean isDeleted = storeService.deleteFromLocal(file);

        if(isDeleted) {
            if(found instanceof Person) fileRepository.deleteByPerson((Person) found);
            else fileRepository.deleteByJob((Job) found);
            return true;
        }
        else return false;
    }

    private <T extends CommonEntity> T checkAndReturn( Class<T> cls, Long id){
        Object found = checkByClass(cls, id);
        if(found == null) throw new NoEntityFoundException(NO_ENTITY_FOUND + " class : " + cls.getName() + ", id : " + id);
        return (T) found;
    }

    private void checkAndJoin( Object obj, File file){
        if(obj instanceof Person) file.setPerson((Person) obj);
        else if (obj instanceof Job) file.setJob((Job) obj);
        else throw new IllegalArgumentException(INVALID_INSTANCE + " 클래스 : " + obj.getClass().getSimpleName());
    }

    private <T extends CommonEntity> T checkByClass(Class cls, Long id){
        if (cls.equals(Person.class)) return (T) personRepository.find(id);
        else if (cls.equals(Job.class)) return (T) jobRepository.find(id);
        else throw new IllegalArgumentException(INVALID_INSTANCE + " class : " + cls.getName());
    }
}
