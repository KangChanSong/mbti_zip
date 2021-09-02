package com.mbtizip.service.file;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

public interface FileService {
    String upload(MultipartFile file);
    void saveFileWithPerson(Long personId, String filename);
    void saveFileWithJob(Long jobId, String filename);

    byte[] loadFileByPerson(Long personId);
    byte[] loadFileByJob(Long jobId);

    void delete(String filename);
    Boolean deleteFileByPerson(Person person);
    Boolean deleteFileByJob(Job job);
}
