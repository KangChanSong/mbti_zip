package com.mbtizip.repository;

import com.mbtizip.domain.comment.Page;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.person.PersonRepository;
import com.mbtizip.repository.test.TestMbtiRepository;
import com.mbtizip.repository.test.TestPersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
        Page page = Page.builder().start(1).end(5).build();
        Mbti mbti = testMbtiRepository.findAll().get(0);

        for(int i = 0 ; i < 10 ; i++){
            testPersonRepository.createPersonWithMbti(mbti);
        }

        //when
        List<Person> findPersons = personRepository.findAllWithPaging(page);

        //then
        assertEquals(findPersons.size(), 5);
    }
}
