package com.mbtizip.service.person;

import com.mbtizip.common.enums.TestCategoryEnum;
import com.mbtizip.common.enums.TestPersonEnum;
import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.personCategory.PersonCategory;
import com.mbtizip.repository.person.PersonRepository;
import com.mbtizip.repository.personCategory.PersonCategoryRepository;
import org.apache.catalina.LifecycleState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.mbtizip.common.enums.TestCategoryEnum.*;
import static com.mbtizip.common.enums.TestPersonEnum.PERSON_DESCRIPTION;
import static com.mbtizip.common.enums.TestPersonEnum.PERSON_NAME;
import static com.mbtizip.common.util.TestEntityGenerator.*;
import static com.mbtizip.domain.mbti.MbtiEnum.INFP;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

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

    
    @DisplayName("인물을 MBTI와 함께 조회하는 테스트")
    @Test
    public void 인물_조회(){

        //given
        Person person = createPerson();
        Mbti mbti = createMbti(INFP);
        person.changeMbti(mbti);

        when(mockPersonRepository.findWithMbti(anyLong())).thenReturn(person);
        //when
        Person findPerson = personService.getById(anyLong());
        //then
        assertEquals(findPerson.getMbti(), mbti);
    }

    @DisplayName("인물 목록을 조회하는 테스트")
    public void 인물_목록_조회(){

        //given

        //when


        //then
    }

    @DisplayName("MBTI 로 인물 목록을 조회하는 테스트 ")
    public void MBTI_인물_목록_조회(){

    }

    @DisplayName("인물을 수정하는 테스트")
    public void 인물_수정(){

    }
    
    @DisplayName("인물을 삭제하는 테스트")
    public void 인물_삭제(){

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
