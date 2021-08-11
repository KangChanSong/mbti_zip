package com.mbtizip.repository;

import com.mbtizip.domain.common.Page;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.person.QPerson;
import com.mbtizip.repository.person.PersonRepository;
import com.mbtizip.repository.test.TestMbtiRepository;
import com.mbtizip.repository.test.TestPersonRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanOperation;
import javassist.compiler.ast.Keyword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    EntityManager em;

    TestPersonRepository testPersonRepository;
    TestMbtiRepository testMbtiRepository;

    @BeforeEach
    public void setUp(){
        testPersonRepository = new TestPersonRepository(em);
        testMbtiRepository = new TestMbtiRepository(em);
    }

    /**
     * 인물 좋아요 증가 확인
     */
    @Test
    public void 좋아요_증가(){

        //given
        Person person = testPersonRepository.createPerson();

        //when
        personRepository.modifyLikes(person, true);

        //then
        assertEquals(person.getLikes(), 1);
    }

    /**
     * 인물 좋아요 감소 확인
     */
    @Test
    public void 좋아요_취소(){

        //given
        Person person = testPersonRepository.createPerson();

        //when
        personRepository.modifyLikes(person, false);

        //then
        assertEquals(person.getLikes(), 0);
    }

    @Test
    public void MBTI와_조회(){

        //given
        Person person =testPersonRepository.createPerson();
        Mbti mbti = testMbtiRepository.findAll().get(0);

        //when
        person.changeMbti(mbti);
        Long saveId = personRepository.save(person);

        //then
        Person findPerson = personRepository.findWithMbti(saveId);
        assertEquals(findPerson.getMbti(), mbti);
    }

    @Test
    public void MBTI가_없을때_조회(){

        //given
        Person person = testPersonRepository.createPerson();
        //when
        Long saveId = personRepository.save(person);
        //then
        Person findPerson = personRepository.findWithMbti(saveId);
        assertNull(findPerson.getMbti());
    }

    @Test
    public void 인물_목록_페이징(){

        //given
        insertMultiplePerson(10);
        Page page = Page.builder().start(1).end(5).build();

        //when
        List<Person> findPersons = personRepository.findAll(page);

        //then
        assertEquals(findPersons.size(), 5);
    }

    @Test
    public void 인물_목록_페이징_정렬(){


        //given
        insertMultiplePerson(10);
        Page page = Page.builder().start(1).end(5).build();

        QPerson qPerson = QPerson.person;
        OrderSpecifier sort = qPerson.id.asc();

        //when
        List<Person> findPersons = personRepository.findAll(page, sort);
        //then
        Person firstOne = findPersons.get(0);
        findPersons.forEach( person -> assertTrue(firstOne.getId() <= person.getId()));

    }

    @Test
    public void 인물_목록_페이징_정렬_검색(){

        //given
        insertMultiplePerson(50);
        Page page = Page.builder().start(1).end(10).build();

        QPerson qPerson = QPerson.person;
        OrderSpecifier sort = qPerson.id.desc();
        BooleanExpression keyword = qPerson.mbti.eq(testMbtiRepository.findAll().get(1));

        //when
        List<Person> findPersons = personRepository.findAll(page, sort, keyword);

        //then
        assertEquals(findPersons.size(), 0);

    }

    private void insertMultiplePerson(int count){
        Mbti mbti = testMbtiRepository.findAll().get(0);
        for(int i=0; i<count; i++){
            testPersonRepository.createPersonWithMbti(mbti);
        }
    }
}
