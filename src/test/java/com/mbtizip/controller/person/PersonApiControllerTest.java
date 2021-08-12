package com.mbtizip.controller.person;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.person.Gender;
import com.mbtizip.domain.person.dto.PersonRegisterDto;
import com.mbtizip.repository.category.CategoryRepository;
import com.mbtizip.repository.person.PersonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.mbtizip.common.enums.TestPersonEnum.PERSON_DESCRIPTION;
import static com.mbtizip.common.enums.TestPersonEnum.PERSON_NAME;
import static com.mbtizip.common.util.TestEntityGenerator.createCategory;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PersonRepository personRepository;

    @DisplayName("/person/register 테스트")
    @Test
    public void 인물_등록(){

        //given
        PersonRegisterDto personDto = createPersonRegisterDto(Gender.MALE);
        List<Long> categoryIds = personDto.getCategoryIds();
        String url = createUrl("register");

        //when
        ResponseEntity<Boolean> registerEntity = restTemplate.postForEntity(url, personDto, Boolean.class);

        //then
        assertEquals(registerEntity.getBody(), true);


    }

    private PersonRegisterDto createPersonRegisterDto(Gender gender){

        PersonRegisterDto personDto = new PersonRegisterDto();
        personDto.setName(PERSON_NAME.getText());
        personDto.setGender(gender.getText());
        personDto.setDescription(PERSON_DESCRIPTION.getText());
        personDto.setCategoryIds(createAndGetCateogryIds());

        return personDto;
    }

    private List<Long> createAndGetCateogryIds(){

        List<Category> categories = new ArrayList<>();
        Category category = createCategory("음악가");
        Category category1 = createCategory("철학가");
        Category category2 = createCategory("연기자");
        categories.add(category);
        categories.add(category1);
        categories.add(category2);


        List<Long> categoryIds = new ArrayList<>();

        categories.forEach(c -> {
            categoryRepository.save(c);
            categoryIds.add(c.getId());
        });

        return categoryIds;

    }


    @DisplayName("/person/get/{personId} 테스트")
    @Test
    public void 인물_조회(){

    }

    @DisplayName("/person/list 테스트")
    @Test
    public void 인물_목록_조회(){

    }

    @DisplayName("/person/update/{personId} 테스트")
    @Test
    public void 인물_수정(){

    }

    @DisplayName("/person/delete/{personId} 테스트")
    @Test
    public void 인물_삭제(){

    }

    @DisplayName("/person/like/{personId} 테스트")
    @Test
    public void 인물_좋아요_증감(){

    }

    private String createUrl(String suffix){
        return "http://localhost:8080/person/api/v1/" + suffix;
    }
}