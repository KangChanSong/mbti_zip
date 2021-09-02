package com.mbtizip.service.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {
    String upload(MultipartFile file);
    Boolean saveFileWithPerson(Long personId, MultipartFile file);
    Boolean saveFileWithJob(Long jobId, MultipartFile file);
    byte[] loadFileByPerson(Long personId);
    byte[] loadFileByJob(Long jobId);

    void delete(String filename);
    Boolean deleteFileByPerson(Long personId);
    Boolean deleteFileByJob(Long jobId);
}
