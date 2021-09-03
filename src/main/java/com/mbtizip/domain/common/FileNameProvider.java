package com.mbtizip.domain.common;

import com.mbtizip.domain.file.File;

public class FileNameProvider {
    public static String getFileName(File file){
        if(file == null) return null;
        else return file.getFileName();
    }
}
