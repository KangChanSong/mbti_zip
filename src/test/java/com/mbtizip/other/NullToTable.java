package com.mbtizip.other;

import com.mbtizip.common.util.TestEntityGenerator;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.candidate.person.Person;
import com.mbtizip.repository.candidate.CandidateRepository;
import com.mbtizip.repository.test.TestPersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
public class NullToTable {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    EntityManager em;

    TestPersonRepository testPersonRepository;

    @BeforeEach
    public void setup(){
        testPersonRepository = new TestPersonRepository(em);
    }

    @Transactional
    //@Test
    public void MBTI_널값으로_했을때_수정되나(){
        //given
        Mbti mbti = TestEntityGenerator.createMbti(MbtiEnum.INFP);
        Person person = testPersonRepository.createPersonWithMbti(mbti);

        //when
        person.changeMbti(null);

        //then
        Person findPerson = (Person) candidateRepository.find(person.getId());

        Assertions.assertEquals(findPerson.getMbti(), null);

    }
}
