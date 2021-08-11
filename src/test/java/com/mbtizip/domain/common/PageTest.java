package com.mbtizip.domain.common;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageTest {
    
    @DisplayName("start 나 end 필드 값이 빌더에 들어오지않으면 기본값을 만들어야 함")
    @Test
    public void Page_빌더(){

        //given
        Page page = Page.builder().build();

        //then
        assertEquals(page.getStart(), 1);
        assertEquals(page.getEnd(), 10);
    }

}