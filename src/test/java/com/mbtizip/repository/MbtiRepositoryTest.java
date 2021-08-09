package com.mbtizip.repository;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.person.Person;
import com.mbtizip.exception.NoEntityFoundException;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.test.MbtiRowCounter;
import com.mbtizip.repository.test.TestJobRepository;
import com.mbtizip.repository.test.TestMbtiRepository;
import com.mbtizip.repository.test.TestPersonRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import java.util.List;

import static com.mbtizip.repository.test.MbtiRowCounter.MBTI_ROW_NUMBER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MbtiRepositoryTest {

    @Autowired
    MbtiRepository mbtiRepository;

    @Autowired
    EntityManager em;

    TestMbtiRepository testMbtiRepository;
    TestJobRepository testJobRepository;
    TestPersonRepository testPersonRepository;

    @BeforeEach
    public void setUp(){
        testMbtiRepository = new TestMbtiRepository(em);
        testJobRepository = new TestJobRepository(em);
        testPersonRepository = new TestPersonRepository(em);
    }

    @Test
    public void MBTI_등록_조회(){
        //given
        Mbti mbti = Mbti.builder()
                .name(MbtiEnum.INFP)
                .build();

        //when
        Long saveId = mbtiRepository.save(mbti);

        //then
        Mbti findMbti = mbtiRepository.find(saveId);

        assertEquals(findMbti.getName(), MbtiEnum.INFP);
    }

    @Test
    public void 조회_실패_예외(){
        //then

        assertThrows(NoEntityFoundException.class, () -> {
            mbtiRepository.find(123L);
        });
    }

    @Test
    public void UNIQUE_KEY_테스트(){

        //given
        Mbti mbti1 = Mbti.builder()
                .name(MbtiEnum.INFP)
                .build();

        Mbti mbti2 = Mbti.builder()
                .name(MbtiEnum.INFP)
                .build();

        //when
        mbtiRepository.save(mbti1);
        mbtiRepository.save(mbti2);

        // 왜 persist 할때는 unique 에 대한 에러가 안나고 조회를 할때 에러가 나지????...
        //then
        Assertions.assertThrows(PersistenceException.class , ()->{
           testMbtiRepository.findAll();
        });

    }

    @Test
    public void 목록_조회(){

        //when
        List<Mbti> mbtis = mbtiRepository.findAll();
        //then
        assertEquals(mbtis.size(), MBTI_ROW_NUMBER);
    }


}