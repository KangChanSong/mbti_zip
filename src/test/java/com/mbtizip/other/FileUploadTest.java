package com.mbtizip.other;

import com.google.common.net.MediaType;
import org.hibernate.bytecode.internal.bytebuddy.BytecodeProviderImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileUploadTest {

    private final String path = "C:/fileupload";

    @Test
    public void test(){
        MediaType jpeg = MediaType.JPEG;
        System.out.println(jpeg.toString());
    }

    @Test
    public void 파일_읽기(){
        Path location = Paths.get(path);
        String filename = "kakaotalk.jpg";
        Path resolved = location.resolve(filename);
        System.out.println(resolved.toString());
    }

    @Test
    public void 파일_삭제(){
        Path location = Paths.get(path);
        String filename = "c0c2ed70-89fe-4505-8a06-3e76786ae229_ScoreVO.jpg";

        File file = location.resolve(filename).toFile();
        boolean isDeleted = file.delete();
        System.out.println(location.resolve(filename));
        System.out.println(file.getAbsolutePath());
        assertTrue(isDeleted);
    }
}
