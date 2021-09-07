package com.mbtizip.service.file.store;

import com.mbtizip.domain.file.File;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService{

    Path rootLocation = Paths.get(PATH_STATIC_UPLOAD);

    @Override
    public void storeInLocal(MultipartFile file, String uuid) {

        try {
            Path destinationFile = this.rootLocation.resolve(uuid + "_" + file.getOriginalFilename())
                    .normalize().toAbsolutePath();

            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException e){
            throw new RuntimeException("파일을 저장하는데 실패했습니다.", e);
        }
    }
    @Override
    public byte[] loadFromLocal(String fullname) {
        try {
            Path loaded = rootLocation.resolve(fullname);

            Resource resource = new UrlResource(loaded.toUri());

            if(resource.exists() || resource.isReadable()){
                return new FileInputStream(resource.getFile()).readAllBytes();
            } else {
                throw new RuntimeException("파일을 읽을 수 없습니다.");
            }
        }
        catch (Exception e){
            throw new RuntimeException("파일을 읽을 수 없습니다.", e);
        }
    }
    @Override
    public boolean deleteFromLocal(File file) {
        String name = file.getFileId().getName();
        String uuid = file.getFileId().getUuid();
        return this.rootLocation
                .resolve(uuid+"_"+name)
                .toFile()
                .delete();
    }

}
