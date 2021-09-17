package com.mbtizip.service.file.file;

import com.mbtizip.domain.file.File;
import com.mbtizip.domain.file.FileId;
import com.mbtizip.repository.file.FileRepository;
import com.mbtizip.service.file.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileDeleteServiceImpl implements FileDeleteService {

    private final FileRepository fileRepository;
    private final StoreService storeService;

    @Value("${file.path}")
    private String path;

    @Override
    public void deleteNotRegisteredFiles() {
        deleteNotRegistered();
        deleteFilesNotInDb();
    }

    public void deleteNotRegistered(){
        log.info("등록되지 않은 이미지 삭제");

        List<File> files = fileRepository.findAllNotRegistered();
        if(files == null || files.isEmpty()) return;

        deleteFromLocal(files);
        deleteFromDb(files);
    }

    public void deleteFilesNotInDb() {
        java.io.File[] files = new java.io.File(path).listFiles();

        if(files == null) return;

        for(java.io.File file : files){
            FileId fileId = new FileId(file.getName());
            Long count = fileRepository.countByFileId(fileId);
            if(count == 0){
                storeService.deleteFromLocal(new File(fileId));
            }
        }
    }

    private void deleteFromDb(List<File> files) {
        files.forEach(file -> fileRepository.delete(file));
    }

    private void deleteFromLocal(List<File> files) {
        files.forEach(file -> storeService.deleteFromLocal(file));
    }
}
