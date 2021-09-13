package com.mbtizip.service.file.store;

import com.mbtizip.domain.file.File;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public interface StoreService {

    //String PATH_STATIC_UPLOAD_LOCAL = "c:/upload/filupload";
    //String PATH_STATIC_UPLOAD = "/home/ec2-user/app/back/upload/";

    void storeInLocal(MultipartFile file, String uuid);
    byte[] loadFromLocal(String filename);
    boolean deleteFromLocal(File file);
}
