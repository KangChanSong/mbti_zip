package com.mbtizip.service.person;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.Page;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.person.QPerson;
import com.mbtizip.repository.category.CategoryRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.mbtiCount.MbtiCountRepository;
import com.mbtizip.repository.person.PersonRepository;
import com.mbtizip.repository.personCategory.PersonCategoryRepository;
import com.mbtizip.service.mbtiCount.MbtiCountService;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mbtizip.common.enums.TestCategoryEnum.*;
import static com.mbtizip.common.util.TestEntityGenerator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    private PersonService personService;

    @Mock
    private PersonRepository mockPersonRepository;

    @Mock
    private PersonCategoryRepository mockPersonCategoryRepository;

    @Mock
    private CategoryRepository mockCategoryRepository;

    @Mock
    private MbtiRepository mbtiRepository;

    @Mock
    private MbtiCountService mbtiCountService;

    @BeforeEach
    public void setup(){
        this.personService = new PersonServiceImpl(mockPersonRepository,
                                                mockPersonCategoryRepository,
                                                    mockCategoryRepository,
                                                    mbtiRepository, mbtiCountService);
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
        Page page = Page.builder().pageNum(11).amount(20).build();
        OrderSpecifier sort = QPerson.person.likes.desc();
        BooleanExpression keyword = QPerson.person.name.eq("송강찬");

        //then
        personService.findAll(null, null, null);
        personService.findAll(page, null, null);
        personService.findAll(page, sort , null);
        personService.findAll(page, sort, keyword);
        personService.findAll(page, null, keyword);
    }

    @DisplayName("인물_카테고리_없을떄")
    @Test
    public void 카테고리_없을떄() throws Exception {

        //given
        List<Person> persons = new ArrayList<>();
        persons.add(createPerson());
        persons.add(createPerson());
        persons.add(createPerson());

        Class cls = Class.forName("com.mbtizip.service.person.PersonServiceImpl");
        Method method = cls.getDeclaredMethod("createPersonMapWithCategories", List.class);
        method.setAccessible(true);
        System.out.println(method.toString());
        //when
        Map resultMap = (Map) method.invoke(personService, persons);

        //then
        System.out.println(resultMap.toString());

        assertEquals(((Map)resultMap).size(), 3);
    }

    //== 편의 메서드 ==//
    private List<Long> createCategoryList(){
        List<Long> categories = new ArrayList<>();
        categories.add(createCategory(CATEGORY_NAME_1).getId());
        categories.add(createCategory(CATEGORY_NAME_2).getId());
        categories.add(createCategory(CATEGORY_NAME_3).getId());
        return categories;
    }

}
