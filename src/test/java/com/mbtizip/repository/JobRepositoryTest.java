package com.mbtizip.repository;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.exception.NoEntityFoundException;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.test.TestMbtiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JobRepositoryTest {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private MbtiRepository mbtiRepository;

    private TestMbtiRepository testMbtiRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        this.testMbtiRepository = new TestMbtiRepository(em);
    }
    
    @Test 
    public void 직업_등록(){
        //given
        String title = "개발자";
        Job job = Job.builder()
                .title(title)
                .build();
        
        //when
        Long saveId = jobRepository.save(job);
        
        //then
        Job findJob = jobRepository.find(saveId);

        assertEquals(findJob.getTitle(), title);
    }

    @Test
    public void 조회_실패_예외(){
        //then

        assertThrows(NoEntityFoundException.class, () -> {
           jobRepository.find(123L);
        });
    }

    @Test
    public void 직업_목록_조회() {

        //given
        List<Mbti> mbtis = testMbtiRepository.findAll();
        List<Job> jobs = new ArrayList<>();

        mbtis.forEach(m -> jobs.add(Job.builder().mbti(m).build()));

        //when
        jobs.forEach(job -> jobRepository.save(job));

        //then
        List<Job> findJobs = jobRepository.findAllWithMbti();

        assertEquals(findJobs.size() , mbtis.size());

        for(int i =0 ; i < mbtis.size() ;i++){
            assertSame(findJobs.get(i).getMbti(), mbtis.get(i));
        }
    }

}