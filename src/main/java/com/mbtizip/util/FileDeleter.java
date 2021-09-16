package com.mbtizip.util;

public class FileDeleter {

    public static final int FILE_LIMIT = 50;
    private static final FileDeleter fileDeleteHelper = new FileDeleter();
    private static int count;

    private FileDeleter(){
        count = 0;
    }

    public static synchronized void deleteFileIfFull(Runnable deleteFile){
        fileDeleteHelper.increaseCount();
        int count = fileDeleteHelper.getCount();
        if(count >= FILE_LIMIT){
            deleteFile.run();
            fileDeleteHelper.resetCount();
        }
    }

    private void increaseCount() {
        count += 1;
    }

    private void resetCount(){
        count = 0;
    }

    private int getCount() {
        return count;
    }

    public static int getSingleToneCount(){
        return fileDeleteHelper.getCount();
    }
}
