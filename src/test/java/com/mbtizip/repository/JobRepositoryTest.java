package com.mbtizip.repository;

import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.job.QJob;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.mbti.QMbti;
import com.mbtizip.exception.NoEntityFoundException;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.test.TestFileRepository;
import com.mbtizip.repository.test.TestJobRepository;
import com.mbtizip.repository.test.TestMbtiRepository;
import com.mbtizip.repository.test.TestRepository;
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
class JobRepositoryTest {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private MbtiRepository mbtiRepository;

    @Autowired
    private EntityManager em;

    private TestRepository testRepository;
    @BeforeEach
    public void setUp(){
        this.testRepository = new TestRepository(em);
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
        MbtiEnum filter = testRepository.getMbtiRepository().findAll().get(0).getName();
        BooleanExpression keyword = QMbti.mbti.name.eq(filter);

        insertMultipleJobs();
        //when
        List<Job> findJobs = jobRepository.findAll(page, sort ,keyword);
        //then
        assertEquals(findJobs.size(), 1);
        assertEquals(findJobs.get(0).getMbti().getName(), filter);
    }

    private void insertMultipleJobs(){
        testRepository.getMbtiRepository().findAll().forEach(
                        mbti -> {
                            testRepository.getJobRepository().createJobWithMbti(mbti);}
        );
    }

    @Test
    public void 좋아요_증감(){

        //given
        Job job = testRepository.getJobRepository().createJob();
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
        Mbti mbti = testRepository.getMbtiRepository().findAll().get(0);
        Mbti modifiedMbti = testRepository.getMbtiRepository().findAll().get(0);
        Job job = testRepository.getJobRepository().createJobWithMbti(mbti);

        //when
        jobRepository.changeMbti(job, modifiedMbti);

        //then
        Job modifiedJob = jobRepository.find(job.getId());
        assertSame(modifiedJob.getMbti(), modifiedMbti);
    }

    @Test
    public void 직업_파일_FETCH_JOIN(){

        //given
        File file = testRepository.getFileRepostiroy().saveAndGetFile();
        Job job = testRepository.getJobRepository().createJob();
        //when
        job.setFile(file);

        //then
        Job found = jobRepository.find(job.getId());
        assertEquals(file ,found.getFile());
    }
}