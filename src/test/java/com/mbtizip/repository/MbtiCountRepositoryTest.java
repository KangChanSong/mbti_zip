package com.mbtizip.repository;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.mbtiCount.MbtiCountRepository;
import com.mbtizip.repository.person.PersonRepository;
import com.mbtizip.repository.test.JobTestHelper;
import com.mbtizip.repository.test.PersonTestHelper;
import com.mbtizip.repository.test.TestMbtiRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mbtizip.repository.test.JobTestHelper.JOB_TITLE;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MbtiCountRepositoryTest {

    @Autowired
    MbtiCountRepository mbtiCountRepository;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    EntityManager em;

    PersonTestHelper personTestHelper;
    JobTestHelper jobTestHelper;
    TestMbtiRepository testMbtiRepository;

    @BeforeEach
    public void setUp(){
        testMbtiRepository = new TestMbtiRepository(em);
        personTestHelper = new PersonTestHelper(personRepository);
        jobTestHelper = new JobTestHelper(jobRepository);
    }

    @Test
    public void MBTI_COUNT_등록_조회_BY_JOB(){
        //given
        // -- Job  먼저 영속성 컨텍스트에 저장 -- //

        Job job = jobTestHelper.createJob();
        MbtiCount mbtiCount = MbtiCount.builder()
                .job(job)
                .count(1)
                .build();

        //when
        Long saveid = mbtiCountRepository.save(mbtiCount);
        //then
        List<MbtiCount> mbtiCounts = mbtiCountRepository.findAllByJob(job.getId());

        assertEquals(mbtiCounts.size() , 1);
        assertEquals(mbtiCounts.get(0).getJob().getTitle(), JOB_TITLE);

    }

    @Test
    public void FIND_MAX_테스트(){

        //given
        Job job = jobTestHelper.createJob();

        MbtiCount mbtiCount1 = MbtiCount.builder().job(job).count(1).build();
        MbtiCount mbtiCount2 = MbtiCount.builder().job(job).count(2).build();
        MbtiCount mbtiCount3 = MbtiCount.builder().job(job).count(3).build();
        MbtiCount mbtiMax = MbtiCount.builder().job(job).count(4).build();

        //when
        mbtiCountRepository.save(mbtiCount1);
        mbtiCountRepository.save(mbtiCount2);
        mbtiCountRepository.save(mbtiCount3);
        mbtiCountRepository.save(mbtiMax);

        //then
        MbtiCount maxObject = mbtiCountRepository.findMaxByJob(job.getId());
        assertTrue(maxObject.getCount() > mbtiCount1.getCount());
        assertTrue(maxObject.getCount() > mbtiCount2.getCount());
        assertTrue(maxObject.getCount() > mbtiCount3.getCount());
    }

    @Test
    public void 직업_카운트_증가_테스트(){

        //given
        Mbti mbti = testMbtiRepository.findAll().get(0);
        Job job = jobTestHelper.createJobWithMbti(mbti);

        //when
        mbtiCountRepository.modifyJobCount(mbti, job, true);

        //then
        MbtiCount max = mbtiCountRepository.findMaxByJob(job.getId());
        assertEquals(max.getCount(), 1);
    }

    @Test
    public void 사람_카운트_증가_테스트(){

        //given
        Mbti mbti = testMbtiRepository.findAll().get(0);
        Person person = personTestHelper.createPersonWithMbti(mbti);

        //when
        mbtiCountRepository.modifyPersonCount(mbti, person, true);

        //then
        MbtiCount max = mbtiCountRepository.findMaxByPerson(person.getId());
    }



}