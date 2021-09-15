package com.mbtizip.repository.file;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.file.FileId;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.candidate.person.Person;

import java.util.List;

public interface FileRepository {
    FileId save(File file);

    Long countByFileId(FileId fileId);

    File find(FileId fileId);
    File findByCandidate(Candidate candidate);
    List<File> findAllNotRegistered();

    void delete(File file);
    void deleteByCandidate(Candidate candidate);
}
