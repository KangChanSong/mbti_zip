package com.mbtizip.domain.person.dto;

import ch.qos.logback.core.util.COWArrayList;
import com.mbtizip.common.enums.TestCategoryEnum;
import com.mbtizip.common.enums.TestPersonEnum;
import com.mbtizip.common.util.TestEntityGenerator;
import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.person.Gender;
import com.mbtizip.domain.person.Person;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mbtizip.common.enums.TestCategoryEnum.*;
import static com.mbtizip.common.enums.TestPersonEnum.PERSON_NAME;
import static com.mbtizip.common.util.TestEntityGenerator.createCategory;
import static com.mbtizip.common.util.TestEntityGenerator.createPerson;
import static org.junit.jupiter.api.Assertions.*;

class PersonGetDtoTest {

    @Test
    public void Dto_생성_테스트(){

        //given
        Person person = createPerson();
        List<Category> categories = new ArrayList<>();
        categories.add(createCategory(CATEGORY_NAME_1));
        categories.add(createCategory(CATEGORY_NAME_2));
        categories.add(createCategory(CATEGORY_NAME_3));

        List<String> names = new ArrayList<>();
        names.add(CATEGORY_NAME_1.getText());
        names.add(CATEGORY_NAME_2.getText());
        names.add(CATEGORY_NAME_3.getText());

        //when
        PersonGetDto dto = PersonGetDto.toDto(person, categories);

        //then
        assertEquals(dto.getCategories(), names);
        assertEquals(dto.getName(), PERSON_NAME.getText());
        assertEquals(dto.getGender(), Gender.MALE.getText());

    }

    @Test
    public void Dto_List_생성_테스트(){

        //given
        Map<Person, List<Category>> map = new HashMap<>();

        List<Category> categories = new ArrayList<>();
        categories.add(createCategory(CATEGORY_NAME_1));
        categories.add(createCategory(CATEGORY_NAME_2));
        categories.add(createCategory(CATEGORY_NAME_3));

        map.put(createPerson(), categories);

        //when
        PersonListDto listDto = PersonListDto.toDtoList(map);

        //then
        PersonGetDto dto = listDto.getPersonGetDtos().get(0);

        assertEquals(listDto.getPersonGetDtos().size() , 1);
        assertEquals(dto.getName(), PERSON_NAME.getText());
        assertTrue(dto.getCategories().contains(categories.get(0).getName()));
    }
}