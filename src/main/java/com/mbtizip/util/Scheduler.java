package com.mbtizip.util;

import com.mbtizip.domain.file.File;
import com.mbtizip.repository.file.FileRepository;
import com.mbtizip.service.file.FileService;
import com.mbtizip.service.file.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {

    private final FileRepository fileRepository;
    private final StoreService storeService;

    /**
     * 한시간마다 등록되지 않은 이미지를 삭제한다.
     */
    @Transactional
    @Scheduled(fixedRate = 3600000)
    public void deleteNotRegisteredFiles(){
        log.info("등록되지 않은 이미지 삭제");

        // 인물, 직업과 연관되지 않은 파일들을 모두 찾는다.
        List<File> files = fileRepository.findAllNotRegistered();
        if(files == null || files.isEmpty()) return;

        // 로컬 스토리지에서 파일들을 삭제한다.
        deleteFromLocal(files);
        // 데이터베이스에서 파일들을 삭제한다.
        deleteFromDb(files);
    }

    private void deleteFromDb(List<File> files) {
        files.forEach(file -> fileRepository.delete(file));
    }

    private void deleteFromLocal(List<File> files) {
        files.forEach(file -> storeService.deleteFromLocal(file));
    }
}
