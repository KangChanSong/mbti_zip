package com.mbtizip.repository;

import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.file.FileId;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.person.QPerson;
import com.mbtizip.repository.file.FileRepository;
import com.mbtizip.repository.person.PersonRepository;
import com.mbtizip.repository.test.TestFileRepository;
import com.mbtizip.repository.test.TestMbtiRepository;
import com.mbtizip.repository.test.TestPersonRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
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
    TestFileRepository testFileRepository;

    @BeforeEach
    public void setUp(){
        testPersonRepository = new TestPersonRepository(em);
        testMbtiRepository = new TestMbtiRepository(em);
        testFileRepository = new TestFileRepository(em);
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
        Person findPerson = personRepository.find(saveId);
        assertEquals(findPerson.getMbti(), mbti);
    }

    @Test
    public void MBTI가_없을때_조회(){

        //given
        Person person = testPersonRepository.createPerson();
        //when
        Long saveId = personRepository.save(person);
        //then
        Person findPerson = personRepository.find(saveId);
        assertNull(findPerson.getMbti());
    }

    @Test
    public void 인물_목록_페이징(){

        //given
        insertMultiplePerson(10);
        Page page = Page.builder().pageNum(1).amount(5).build();

        //when
        List<Person> findPersons = personRepository.findAll(page);

        //then
        assertEquals(findPersons.size(), 5);
    }

    @Test
    public void 인물_목록_페이징_정렬(){


        //given
        insertMultiplePerson(10);
        Page page = Page.builder().pageNum(1).amount(5).build();

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
        Page page = Page.builder().pageNum(1).amount(10).build();

        QPerson qPerson = QPerson.person;
        OrderSpecifier sort = qPerson.id.desc();
        BooleanExpression keyword = qPerson.mbti.eq(testMbtiRepository.findAll().get(1));

        //when
        List<Person> findPersons = personRepository.findAll(page, sort, keyword);

        //then
        assertEquals(findPersons.size(), 0);

    }

    @Test
    public void 인물_목록_조회_MBTI(){

        //given
        insertMultiplePerson(10);
        //when
        Page page = Page.builder().build();
        OrderSpecifier sort = QPerson.person.createDate.desc();

        MbtiEnum name = testMbtiRepository.findAll().get(0).getName();
        BooleanExpression keyword = QPerson.person.mbti.name.eq(name);
        List<Person> findPersons = personRepository.findAll(page, sort, keyword);
        //then
        assertEquals(findPersons.size(), 10);
        findPersons.forEach( person -> assertEquals(person.getMbti().getName() , name));
    }

    @Test
    public void 인물_카테고리_파일_조인(){

        //given
        Person savePerson = testPersonRepository.createPerson();
        File file = testFileRepository.saveAndGetFile();

        //when
        file.setPerson(savePerson);

        //then
        Person found = personRepository.find(savePerson.getId());
        assertEquals(found.getFile(), file);
    }

    private void insertMultiplePerson(int count){
        Mbti mbti = testMbtiRepository.findAll().get(0);
        for(int i=0; i<count; i++){
            testPersonRepository.createPersonWithMbti(mbti);
        }
    }


}
