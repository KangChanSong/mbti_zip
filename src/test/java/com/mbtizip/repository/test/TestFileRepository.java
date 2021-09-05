package com.mbtizip.repository.test;

import com.mbtizip.domain.file.File;
import com.mbtizip.domain.file.FileId;

import javax.persistence.EntityManager;

import static com.mbtizip.common.enums.TestFileEnum.FILE_NAME;
import static com.mbtizip.common.enums.TestFileEnum.FILE_UUID;

public class TestFileRepository {

    private final EntityManager em;

    public TestFileRepository(EntityManager em) {
        this.em = em;
    }

    public File saveAndGetFile(){
        File file = new File(FileId.builder().uuid(FILE_UUID.getText())
                                .name(FILE_NAME.getText())
                                .build());

        em.persist(file);

        return file;
    }
}
