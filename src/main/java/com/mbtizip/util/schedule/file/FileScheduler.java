package com.mbtizip.util.schedule.file;

public interface FileScheduler {

    void deleteNotRegisteredFiles();
    void deleteFilesNotInDb();
}
