package com.mbtizip.domain.common;

import com.mbtizip.domain.common.pageSortFilter.Page;
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
        assertEquals(page.getPageNum(), 1);
        assertEquals(page.getAmount(), 10);

        //given
        Page page1 = Page.builder().pageNum(3).build();
        //then
        assertEquals(page1.getOffset(), 20);
        assertEquals(page1.getAmount(), 10);
    }

}