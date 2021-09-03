package com.mbtizip.service.file.store;

import com.mbtizip.domain.file.File;
import org.springframework.web.multipart.MultipartFile;

public interface StoreService {
    void storeInLocal(MultipartFile file, String uuid);
    byte[] loadFromLocal(String filename);
    boolean deleteFromLocal(File file);
}
