package com.mbtizip.repository;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.personCategory.PersonCategory;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.personCategory.PersonCategoryRepository;
import com.mbtizip.repository.test.TestCategoryRepository;
import com.mbtizip.repository.test.TestPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PersonCategoryRepositoryTest {
    @Autowired
    PersonCategoryRepository personCategoryRepository;

    @Autowired
    EntityManager em;

    TestPersonRepository testPersonRepository;
    TestCategoryRepository testCategoryRepository;

    @BeforeEach
    public void setUp(){
        testPersonRepository = new TestPersonRepository(em);
        testCategoryRepository = new TestCategoryRepository(em);
    }

    /**
     * 한 인물을 여러 카테고리와 함께 등록
     */
    @Test
    public void 여러_카테고리_등록(){
        //given
        List<Category> categories = new ArrayList<>();
        int count = 10;

        for(int i=0; i<count ;i++){
            categories.add(testCategoryRepository.createCategory("category" + i));
        }

        Person person = testPersonRepository.createPerson();

        categories.forEach(
                i -> personCategoryRepository.save(
                        PersonCategory.builder()
                                .person(person)
                                .category(i)
                                .build()
                ));

        //when
        List<PersonCategory> findList = personCategoryRepository.findAllByPerson(person);

        //then
        assertEquals(findList.size(), count);
        findList.forEach( i -> assertSame(i.getPerson() , person));
    }

    /**
     * 카테고리로 인물 조회
     */
    @Test
    public void 카테고리로_인물_조회(){
        //given
        List<Person> persons = new ArrayList<>();
        int count = 10;
        for(int i = 0 ; i < count ; i++ ){
            persons.add(
                    testPersonRepository.createPerson("person" + i)
            );
        }

        Category category = testCategoryRepository.createCategory();

        //when
        persons.forEach( i-> personCategoryRepository.save(
                PersonCategory.builder()
                        .person(i)
                        .category(category)
                        .build()));

        //then
        List<PersonCategory> personCategories = personCategoryRepository.findAllByCategory(category);

        assertEquals(personCategories.size() , count);
        personCategories.forEach(i -> assertSame(i.getCategory(), category));
    }

}
