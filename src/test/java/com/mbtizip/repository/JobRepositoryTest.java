package com.mbtizip.repository;

import com.mbtizip.common.enums.TestJobEnum;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.exception.NoEntityFoundException;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.test.TestJobRepository;
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
    private TestJobRepository testJobRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp(){
        this.testMbtiRepository = new TestMbtiRepository(em);
        this.testJobRepository = new TestJobRepository(em);
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

        mbtis.forEach(m -> {
                Job job = Job.builder().build();
                job.changeMbti(m);
                jobs.add(job);});

        //when
        jobs.forEach(job -> jobRepository.save(job));

        //then
        List<Job> findJobs = jobRepository.findAllWithMbti();

        assertEquals(findJobs.size() , mbtis.size());

        for(int i =0 ; i < mbtis.size() ;i++){
            assertSame(findJobs.get(i).getMbti(), mbtis.get(i));
        }
    }

    @Test
    public void 좋아요_증감(){

        //given
        Job job = testJobRepository.createJob();
        //when
        jobRepository.modifyLikes(job, true);
        jobRepository.modifyLikes(job, false);

        //then
        Job findJob = jobRepository.find(job.getId());
        assertEquals(findJob.getLikes(), 0);
    }
    
    @Test
    public void MBTI_로_조회(){

        //given
        Mbti mbti = testMbtiRepository.findAll().get(0);
        List<Job> jobs = new ArrayList<>();

        int count = 10;
        for(int i = 0 ; i < count ; i++){
            jobs.add(testJobRepository.createJobWithMbti("title" + i, mbti));
        }

        //when
        List<Job> findJobs = jobRepository.findAllByMbti(mbti);

        //then
        assertEquals(findJobs.size() , count);
        findJobs.forEach( i -> assertSame(i.getMbti(), mbti));
    }

    @Test
    public void MBTI_변경(){

        //given
        Mbti mbti = testMbtiRepository.findAll().get(0);
        Mbti modifiedMbti = testMbtiRepository.findAll().get(0);
        Job job = testJobRepository.createJobWithMbti(mbti);

        //when
        jobRepository.changeMbti(job, modifiedMbti);

        //then
        Job modifiedJob = jobRepository.find(job.getId());
        assertSame(modifiedJob.getMbti(), modifiedMbti);
    }

    @Test
    public void MBTI_디폴트_값_테스트(){

        //given
        Mbti mbti  = testMbtiRepository.findAll().get(0);
        Job job = Job.builder().title(TestJobEnum.JOB_TITLE.getText()).build();
        //when
        Long saveId = jobRepository.save(job);

        //then

        Job findJob1 = jobRepository.find(saveId);
        assertEquals(findJob1.getMbti().getName(), MbtiEnum.NONE);

        job.changeMbti(mbti);
        Job findJob2 = jobRepository.find(saveId);
        assertEquals(findJob2.getMbti(), mbti);
    }

}