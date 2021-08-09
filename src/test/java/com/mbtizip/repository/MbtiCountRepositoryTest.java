package com.mbtizip.repository;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.mbtiCount.MbtiCountRepositoryImpl;
import com.mbtizip.repository.test.TestJobRepository;
import com.mbtizip.repository.test.TestMbtiRepository;
import com.mbtizip.repository.test.TestPersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mbtizip.repository.test.TestJobRepository.JOB_TITLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class MbtiCountRepositoryTest {



    @Autowired
    EntityManager em;

    //== 테스트 대역 ==//
    ChildMbtiCountRepository childMbtiCountRepository;
    TestPersonRepository testPersonRepository;
    TestJobRepository testJobRepository;
    TestMbtiRepository testMbtiRepository;

    @BeforeEach
    public void setUp(){
        testMbtiRepository = new TestMbtiRepository(em);
        testPersonRepository = new TestPersonRepository(em);
        testJobRepository = new TestJobRepository(em);
        childMbtiCountRepository = new ChildMbtiCountRepository(em);
    }

    @Test
    public void MBTI_COUNT_등록_조회_BY_JOB(){
        //given
        // -- Job  먼저 영속성 컨텍스트에 저장 -- //

        Job job = testJobRepository.createJob();
        MbtiCount mbtiCount = MbtiCount.builder()
                .job(job)
                .count(1)
                .build();

        //when
        Long saveid = childMbtiCountRepository.save(mbtiCount);
        //then
        List<MbtiCount> mbtiCounts = childMbtiCountRepository.findAllByJob(job.getId());

        assertEquals(mbtiCounts.size() , 1);
        assertEquals(mbtiCounts.get(0).getJob().getTitle(), JOB_TITLE);

    }

    @Test
    public void FIND_MAX_테스트(){

        //given
        Job job = testJobRepository.createJob();

        MbtiCount mbtiCount1 = MbtiCount.builder().job(job).count(1).build();
        MbtiCount mbtiCount2 = MbtiCount.builder().job(job).count(2).build();
        MbtiCount mbtiCount3 = MbtiCount.builder().job(job).count(3).build();
        MbtiCount mbtiMax = MbtiCount.builder().job(job).count(4).build();

        //when
        childMbtiCountRepository.save(mbtiCount1);
        childMbtiCountRepository.save(mbtiCount2);
        childMbtiCountRepository.save(mbtiCount3);
        childMbtiCountRepository.save(mbtiMax);

        //then
        MbtiCount maxObject = childMbtiCountRepository.findMaxByJob(job);
        assertTrue(maxObject.getCount() > mbtiCount1.getCount());
        assertTrue(maxObject.getCount() > mbtiCount2.getCount());
        assertTrue(maxObject.getCount() > mbtiCount3.getCount());
    }

    @Test
    public void 직업_카운트_증가_테스트(){

        //given
        Mbti mbti = testMbtiRepository.findAll().get(0);
        Job job = testJobRepository.createJobWithMbti(mbti);

        //when
        childMbtiCountRepository.modifyJobCount(mbti, job, true);

        //then
        MbtiCount max = childMbtiCountRepository.findMaxByJob(job);
        List<MbtiCount> counts = childMbtiCountRepository.findAllByJob(job.getId());

        assertEquals(counts.size(), 1);
        assertEquals(max.getCount(), 1);
    }

    @Test
    public void 사람_카운트_증가_테스트(){

        //given
        Mbti mbti = testMbtiRepository.findAll().get(0);
        Person person = testPersonRepository.createPersonWithMbti(mbti);

        //when
        childMbtiCountRepository.modifyPersonCount(mbti, person, true);

        //then
        MbtiCount max = childMbtiCountRepository.findMaxByPerson(person);
        List<MbtiCount> counts = childMbtiCountRepository.findAllByPerson(person.getId());

        assertEquals(counts.size(), 1);
        assertEquals(max.getCount(), 1);
    }

    @Test
    public void 감소_테스트(){

        //given
        int count = 1;

        //when
        MbtiCount mbtiCount = decreaseAndGet(count);

        //then
        assertEquals(mbtiCount.getCount(), count-1);
    }

    @Test
    public void 감소_테스트_0일떄(){
        //when
        MbtiCount mbtiCount = decreaseAndGet(0);
        //then
        assertEquals(mbtiCount.getCount(), 0);

    }

    @Test
    public void 두번_이상_증가(){

        //given
        Mbti mbti = testMbtiRepository.findAll().get(0);
        Job job = testJobRepository.createJobWithMbti(mbti);

        int count = 10;
        //when
        for(int i = 0 ; i < 10 ; i++){
            childMbtiCountRepository.modifyJobCount(mbti, job , true);
        }

        //then
        MbtiCount max = childMbtiCountRepository.findMaxByJob(job);
    }

    /**
     *
     * @param count MbtiCount 를 등록할때의 카운트 수
     * @return count 를 1 감소시키고 엔티티 반환 
     *          0일때는 감소안됨
     */
    private MbtiCount decreaseAndGet(int count){
        Mbti mbti = testMbtiRepository.findAll().get(0);
        Job job = testJobRepository.createJobWithMbti(mbti);

        MbtiCount mbtiCount = MbtiCount.builder()
                .mbti(mbti)
                .job(job)
                .count(count)
                .build();
        childMbtiCountRepository.save(mbtiCount);

        assertEquals(mbtiCount.getCount() , count);

        childMbtiCountRepository.modifyJobCount(mbti, job , false);

        return mbtiCount;
    }

    /**
     * static 클래스
     */
    static class ChildMbtiCountRepository extends MbtiCountRepositoryImpl{

        private final EntityManager thisEm;

        public ChildMbtiCountRepository(EntityManager em) {
            super(em);
            thisEm = em;
        }

        public Long save(MbtiCount mbtiCount){
            thisEm.persist(mbtiCount);
            return mbtiCount.getId();
        }
    }


}