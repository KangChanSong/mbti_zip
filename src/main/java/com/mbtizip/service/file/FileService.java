package com.mbtizip.service.file;

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
    Boolean deleteFileByPerson(Long personId);
    Boolean deleteFileByJob(Long jobId);
}
