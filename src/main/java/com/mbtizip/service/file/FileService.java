package com.mbtizip.service.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {
    Boolean saveFile(Long personId, MultipartFile file);
    byte[] loadFile(Long personId);
    Boolean deleteFile(Long personId);
}
