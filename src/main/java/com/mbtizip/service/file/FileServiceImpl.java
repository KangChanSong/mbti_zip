package com.mbtizip.service.file;

import com.mbtizip.domain.common.CommonEntity;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.file.FileId;
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
import java.awt.image.MultiResolutionImage;
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
import static java.util.UUID.randomUUID;

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
    public String upload(MultipartFile multipartFile) {
        FileId saved = fileRepository.save(new File(multipartFile));
        storeService.storeInLocal(multipartFile, saved.getUuid());
        return saved.getUuid() + "_" + saved.getName();
    }
    @Transactional
    @Override
    public void saveFileWithPerson(Long personId, String filename) {
        Person findPerson = checkAndReturn(Person.class, personId);
        File file = fileRepository.find(new FileId(filename));
        file.setPerson(findPerson);
    }
    @Transactional
    @Override
    public void saveFileWithJob(Long jobId, String filename) {
        Job findJob = checkAndReturn(Job.class,jobId);
        File file = fileRepository.find(new FileId(filename));
        file.setJob(findJob);
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
    public void delete(String filename) {
        File file = fileRepository.find(new FileId(filename));
        fileRepository.delete(file);
        storeService.deleteFromLocal(file);
    }
    @Transactional
    @Override
    public Boolean deleteFileByPerson(Person person) {
        return deleteFileByObject(person);
    }

    @Transactional
    @Override
    public Boolean deleteFileByJob(Job job) {
        return deleteFileByObject(job);
    }

    //== private 메서드 ==//

    private Boolean deleteFileByObject(Object obj){
        Optional<File> optFile;
        if(obj instanceof Person) optFile = Optional.of(fileRepository.findByPerson((Person) obj));
        else optFile = Optional.of(fileRepository.findByJob((Job) obj));

        File file = optFile.orElseThrow(() -> {throw new IllegalArgumentException(NO_ENTITY_FOUND);});
        boolean isDeleted = storeService.deleteFromLocal(file);

        if(isDeleted) {
            if(obj instanceof Person) fileRepository.deleteByPerson((Person) obj);
            else fileRepository.deleteByJob((Job) obj);
            return true;
        }
        else return false;
    }

    private <T extends CommonEntity> T checkAndReturn( Class<T> cls, Long id){
        Object found = checkByClass(cls, id);
        if(found == null) throw new NoEntityFoundException(NO_ENTITY_FOUND + " class : " + cls.getName() + ", id : " + id);
        return (T) found;
    }

    private <T extends CommonEntity> T checkByClass(Class cls, Long id){
        if (cls.equals(Person.class)) return (T) personRepository.find(id);
        else if (cls.equals(Job.class)) return (T) jobRepository.find(id);
        else throw new IllegalArgumentException(INVALID_INSTANCE + " class : " + cls.getName());
    }
}
