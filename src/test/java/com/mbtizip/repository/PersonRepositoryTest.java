package com.mbtizip.repository;

import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.person.PersonRepository;
import com.mbtizip.repository.test.TestPersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    EntityManager em;

    TestPersonRepository testPersonRepository;

    @BeforeEach
    public void setUp(){
        testPersonRepository = new TestPersonRepository(em);
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
}
