package com.mbtizip.util.schedule.file;

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
public class FileSchedulerImpl implements FileScheduler {

    private final FileRepository fileRepository;
    private final StoreService storeService;

    @Value("${file.path}")
    private String path;
    /**
     * 한시간마다 등록되지 않은 이미지를 삭제한다.
     */
    @Override
    public void deleteNotRegisteredFiles() {
        log.info("등록되지 않은 이미지 삭제");

        // 인물, 직업과 연관되지 않은 파일들을 모두 찾는다.
        List<File> files = fileRepository.findAllNotRegistered();
        if(files == null || files.isEmpty()) return;

        // 로컬 스토리지에서 파일들을 삭제한다.
        deleteFromLocal(files);
        // 데이터베이스에서 파일들을 삭제한다.
        deleteFromDb(files);
    }

    @Override
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
