package com.mbtizip.repository;

import com.mbtizip.common.enums.TestJobEnum;
import com.mbtizip.domain.common.Page;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.job.QJob;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.mbti.QMbti;
import com.mbtizip.exception.NoEntityFoundException;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.test.TestJobRepository;
import com.mbtizip.repository.test.TestMbtiRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.hibernate.cfg.annotations.MapBinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static com.mbtizip.domain.mbti.MbtiEnum.ENTP;
import static com.mbtizip.domain.mbti.MbtiEnum.INFP;
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
    public void 직업_페이징(){

        //given
        Page page = Page.builder().pageNum(0).amount(10).build();
        insertMultipleJobs();
        System.out.println(page.toString());
        //when
        List<Job> findJobs = jobRepository.findAll(page);
        //then
        assertEquals(findJobs.size(), 10);
    }

    @Test
    public void 직업_정렬(){

        //given
        Page page = Page.builder().pageNum(0).amount(10).build();
        OrderSpecifier sort = QJob.job.id.desc();

        insertMultipleJobs();
        //when
        List<Job> findJobs = jobRepository.findAll(page, sort);
        //then
        Job firstOne = findJobs.get(0);
        findJobs.forEach(job -> assertTrue(firstOne.getId() >= job.getId()));
    }

    @Test
    public void 직업_검색(){

        //given
        Page page = Page.builder().pageNum(0).amount(10).build();
        OrderSpecifier sort = QJob.job.id.desc();
        MbtiEnum filter = testMbtiRepository.findAll().get(0).getName();
        BooleanExpression keyword = QMbti.mbti.name.eq(filter);

        insertMultipleJobs();
        //when
        List<Job> findJobs = jobRepository.findAll(page, sort ,keyword);
        //then
        assertEquals(findJobs.size(), 1);
        assertEquals(findJobs.get(0).getMbti().getName(), filter);
    }

    private void insertMultipleJobs(){
        testMbtiRepository.findAll().forEach(
                        mbti -> {
                            testJobRepository.createJobWithMbti(mbti);}
        );
    }

    @Test
    public void 좋아요_증감(){

        //given
        Job job = testJobRepository.createJob();
        //when
        jobRepository.modifyLikes(job, true);

        //then
        Job findJob1 = jobRepository.find(job.getId());
        assertEquals(findJob1.getLikes(), 1);
        //when
        jobRepository.modifyLikes(job, false);

        //then
        Job findJob2 = jobRepository.find(job.getId());
        assertEquals(findJob2.getLikes(), 0);
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
}