package com.mbtizip.domain.file;

import com.mbtizip.common.enums.TestFileEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.mbtizip.common.enums.TestFileEnum.FILE_NAME;
import static com.mbtizip.common.enums.TestFileEnum.FILE_UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class FileIdTest {

    @DisplayName("언더스코어가 여러개 있는 문자열에 대해 uuid 와 파일이름을 적절히 나누는지에 대한 테스트")
    @Test
    public void test1(){
        //given
        String fullname = FILE_UUID.getText() + "_" + FILE_NAME.getText();

        //when
        FileId fileId = new FileId(fullname);

        //then
        assertNotEquals(FILE_UUID.getText(), fileId.getUuid());
        assertNotEquals(FILE_NAME.getText(), fileId.getName());
        assertEquals("file", fileId.getUuid());
        assertEquals("uuid_file_name", fileId.getName());
    }
}
