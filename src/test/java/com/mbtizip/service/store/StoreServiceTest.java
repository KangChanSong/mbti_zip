package com.mbtizip.service.store;

import com.mbtizip.service.file.store.StoreService;
import com.mbtizip.service.file.store.StoreServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class StoreServiceTest {

    StoreService storeService;

    @BeforeEach
    public void setup(){
        storeService = new StoreServiceImpl();
    }

    @Test
    public void 파일_저장(){
        Path rootLocation = Paths.get("C:/fileupload");
        Assertions.assertNotNull(rootLocation.resolve("asdasdas").normalize().toAbsolutePath());
    }
}
