package com.mbtizip.domain.common;

import com.mbtizip.domain.candidate.person.Gender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumValueOfTest {

    @Test
    public void valueOf(){
        Gender gender = Gender.valueOf("MALE");
        assertEquals(gender.getText(), "male");
    }
}
