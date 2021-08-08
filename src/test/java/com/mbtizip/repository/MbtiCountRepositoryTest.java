package com.mbtizip.repository;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.mbtiCount.MbtiCountRepository;
import com.mbtizip.repository.person.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MbtiCountRepositoryTest {

    @Autowired
    MbtiCountRepository mbtiCountRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    JobRepository jobRepository;

    @Test
    public void MBTI_COUNT_등록_조회_BY_JOB(){
        //given
        // -- Job  먼저 영속성 컨텍스트에 저장 -- //
        String jobTitle = "개발자";
        Job job = Job.builder().title(jobTitle).build();
        Long jobId = jobRepository.save(job);

        MbtiCount mbtiCount = MbtiCount.builder()
                .job(job)
                .count(1)
                .build();

        //when
        Long saveid = mbtiCountRepository.save(mbtiCount);
        //then
        List<MbtiCount> mbtiCounts = mbtiCountRepository.findAllByJob(job.getId());

        assertEquals(mbtiCounts.size() , 1);
        assertEquals(mbtiCounts.get(0).getJob().getTitle(), jobTitle);

    }

    @Test
    public void FIND_MAX_테스트(){

        //given
        String jobTitle = "개발자";
        Job job = Job.builder().title(jobTitle).build();
        Long jobId = jobRepository.save(job);

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
        MbtiCount maxObject = mbtiCountRepository.findMaxByJob(jobId);
        assertTrue(maxObject.getCount() > mbtiCount1.getCount());
        assertTrue(maxObject.getCount() > mbtiCount2.getCount());
        assertTrue(maxObject.getCount() > mbtiCount3.getCount());
    }
}