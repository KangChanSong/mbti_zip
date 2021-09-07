package com.mbtizip.service.file.store;

import com.mbtizip.domain.file.File;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public interface StoreService {

    String PATH_STATIC_UPLOAD = "src/main/resources/static/upload/";

    void storeInLocal(MultipartFile file, String uuid);
    byte[] loadFromLocal(String filename);
    boolean deleteFromLocal(File file);
}
