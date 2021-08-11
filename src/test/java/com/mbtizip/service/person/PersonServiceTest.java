package com.mbtizip.service.person;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.Page;
import com.mbtizip.domain.person.QPerson;
import com.mbtizip.repository.person.PersonRepository;
import com.mbtizip.repository.personCategory.PersonCategoryRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.mbtizip.common.enums.TestCategoryEnum.*;
import static com.mbtizip.common.util.TestEntityGenerator.createCategory;
import static com.mbtizip.common.util.TestEntityGenerator.createPerson;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    private PersonService personService;

    @Mock
    private PersonRepository mockPersonRepository;

    @Mock
    private PersonCategoryRepository mockPersonCategoryRepository;

    @BeforeEach
    public void setup(){
        this.personService = new PersonServiceImpl(mockPersonRepository,
                                                mockPersonCategoryRepository);
    }

    @DisplayName("인물을 카테고리와 함께 등록 시 카테고리가 영속성 컨텍스트에 없으면 예외 처리")
    @Test
    public void 인물_등록(){

        assertThrows(IllegalArgumentException.class, () ->
                personService.registerWithCategory(createPerson(), createCategoryList()));


    }
    @DisplayName("페이징 목록 조회 테스트 ")
    @Test
    public void findAll_파라미터(){

        //given
        Page page = Page.builder().start(11).end(20).build();
        OrderSpecifier sort = QPerson.person.likes.desc();
        BooleanExpression keyword = QPerson.person.name.eq("송강찬");

        //then
        personService.findAll(null, null, null);
        personService.findAll(page, null, null);
        personService.findAll(page, sort , null);
        personService.findAll(page, sort, keyword);
        personService.findAll(page, null, keyword);
    }

    //== 편의 메서드 ==//
    private List<Category> createCategoryList(){
        List<Category> categories = new ArrayList<>();
        categories.add(createCategory(CATEGORY_NAME_1));
        categories.add(createCategory(CATEGORY_NAME_2));
        categories.add(createCategory(CATEGORY_NAME_3));
        return categories;
    }

}
