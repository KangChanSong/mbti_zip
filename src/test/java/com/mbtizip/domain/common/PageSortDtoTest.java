package com.mbtizip.domain.common;

import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.person.QPerson;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PageSortDtoTest {

    @Test
    public void Page_생성_테스트(){

        //given
        PageSortFilterDto<Person> psf = new PageSortFilterDto<>();
        //when
        psf.setPage(2);
        psf.setSize(10);

        //then
        Page page = psf.toPage();
        assertEquals(page.getPageNum(), 2);
        assertEquals(page.getAmount(), 10);
        assertEquals(page.getOffset(), 10);
    }

    @Test
    public void Person_Sort_생성_테스트(){

        //given
        PageSortFilterDto<Person> psf = new PageSortFilterDto<>();
        //when
        psf.setSort("createDate");
        psf.setDir("ascadasd");
        //then
        OrderSpecifier sort = psf.toPersonSort();
        assertEquals(sort, QPerson.person.createDate.desc());
    }

    @Test
    public void Person_Keyword_생성_테스트(){

        //given
        PageSortFilterDto<Person> psf = new PageSortFilterDto<>();

        //when
        psf.setKeyword("INFP");
        psf.setFilterBy("mbti");

        //then
        BooleanExpression keyword = psf.toPersonKeyword();
        assertEquals(keyword, QPerson.person.mbti.name.eq(MbtiEnum.INFP));
    }

    @Test
    public void NULL_테스트(){

        //given
        PageSortFilterDto psf=  new PageSortFilterDto();
        //when
        Page page = psf.toPage();
        OrderSpecifier sort = psf.toPersonSort();
        //then
        assertNotNull(page);
        assertNotNull(sort);
        assertThrows(IllegalArgumentException.class , () -> psf.toPersonKeyword());
    }
}
