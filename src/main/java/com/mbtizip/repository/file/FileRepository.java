package com.mbtizip.repository.file;

import com.mbtizip.domain.file.File;
import com.mbtizip.domain.file.FileId;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;

import java.util.List;

public interface FileRepository {
    FileId save(File file);

    Long countByFileId(FileId fileId);

    File find(FileId fileId);
    File findByPerson(Person person);
    File findByJob(Job job);
    List<File> findAllNotRegistered();

    void delete(File file);
    void deleteByPerson(Person job);
    void deleteByJob(Job job);
}
