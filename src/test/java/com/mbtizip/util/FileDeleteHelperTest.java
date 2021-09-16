package com.mbtizip.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileDeleteHelperTest {

    @DisplayName("서로 다른 스레드에서 count 를 증가시켜 10 이 됐을때 다시 0 으로 초기화 된다.")
    @Test
    public void static_필드_증가(){

        //given
        Thread t1 = new Thread(() -> callDeleteFile());
        Thread t2 = new Thread(() -> callDeleteFile());
        //then
        t1.start();
        t2.start();

        Assertions.assertEquals(0, FileDeleter.getSingleToneCount());
    }

    private void callDeleteFile(){
        for(int i = 0; i < FileDeleter.FILE_LIMIT / 2 ; i ++){
            FileDeleter.deleteFileIfFull(() -> {
            });
        }
    }
}