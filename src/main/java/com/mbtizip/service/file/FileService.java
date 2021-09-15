package com.mbtizip.service.file;

import com.mbtizip.domain.file.File;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.candidate.person.Person;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    File getByPerson(Person person);
    File getByJob(Job job);

    String upload(MultipartFile file);
    void saveFileWithPerson(Long personId, String filename);
    void saveFileWithJob(Long jobId, String filename);
    void delete(String filename);
    Boolean deleteFileByPerson(Person person);
    Boolean deleteFileByJob(Job job);
}
