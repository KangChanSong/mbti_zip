package com.mbtizip.other;

import com.mbtizip.dummies.DummyInserter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class EnvironmentTest {

    @Autowired
    DummyInserter dummyInserter;

    @Test
    public void is_true(){
        assertFalse(dummyInserter.getInsertDummies());
    }
}
