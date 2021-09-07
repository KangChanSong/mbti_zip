package com.mbtizip.util.schedule;
import com.mbtizip.util.schedule.file.FileScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {
    private final FileScheduler fileScheduler;

    @Transactional
    @Scheduled(fixedRate = 3600000)
    public void deleteFiles(){
        fileScheduler.deleteNotRegisteredFiles();
        fileScheduler.deleteFilesNotInDb();
    }
}
