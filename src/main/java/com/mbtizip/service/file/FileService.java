package com.mbtizip.service.file;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.candidate.person.Person;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    File getByCandidate(Candidate candidate);

    String upload(MultipartFile file);
    void saveFileWithCandidate(Long id, String fullname);
    void delete(String filename);
    Boolean deleteFileWithCandidate(Candidate candidate);
}
