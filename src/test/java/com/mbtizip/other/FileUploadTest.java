package com.mbtizip.other;

import com.google.common.net.MediaType;
import org.hibernate.bytecode.internal.bytebuddy.BytecodeProviderImpl;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadTest {

    @Test
    public void test(){
        MediaType jpeg = MediaType.JPEG;
        System.out.println(jpeg.toString());
    }

    @Test
    public void 파일_읽기(){
        Path location = Paths.get("C:/temp_files");
        String filename = "kakaotalk.jpg";
        Path resolved = location.resolve(filename);
        System.out.println(resolved.toString());
    }
}
