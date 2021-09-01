package com.mbtizip.service.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {
    Boolean saveFileWithPerson(Long personId, MultipartFile file);
    Boolean saveFileWithJob(Long jobId, MultipartFile file);
    byte[] loadFileByPerson(Long personId);
    byte[] loadFileByJob(Long jobId);

    Boolean deleteFileByPerson(Long personId);
    Boolean deleteFileByJob(Long jobId);
}
