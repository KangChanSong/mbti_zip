package com.mbtizip.repository;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.mbti.MbtiRepositoryImpl;
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
import java.util.stream.IntStream;

import static com.mbtizip.repository.test.TestJobRepository.JOB_TITLE;
import static org.junit.jupiter.api.Assertions.*;

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
                .build();

        mbtiCount.updateCount(true);

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
        Mbti mbti = testMbtiRepository.findAll().get(0);

        MbtiCount mbtiCount1 = MbtiCount.builder().job(job).build();
        IntStream.range(0, 1).forEach( i -> mbtiCount1.updateCount(true));

        MbtiCount mbtiCount2 = MbtiCount.builder().job(job).build();
        IntStream.range(0, 2).forEach( i-> mbtiCount2.updateCount(true));

        MbtiCount mbtiCount3 = MbtiCount.builder().job(job).build();
        IntStream.range(0, 3).forEach(i -> mbtiCount3.updateCount(true));

        MbtiCount mbtiMax1 = MbtiCount.builder()
                .mbti(mbti)
                .job(job)
                .build();

        MbtiCount mbtiMax2 = MbtiCount.builder()
                .mbti(mbti)
                .job(job)
                .build();

        IntStream.range(0, 4).forEach(i -> {
            mbtiMax1.updateCount(true);
            mbtiMax2.updateCount(true);
        });

        //when
        childMbtiCountRepository.save(mbtiCount1);
        childMbtiCountRepository.save(mbtiCount2);
        childMbtiCountRepository.save(mbtiCount3);
        childMbtiCountRepository.save(mbtiMax1);

        //then
        MbtiCount maxObject = childMbtiCountRepository.findMaxByJob(job).get(0);
        assertTrue(maxObject.getCount() > mbtiCount1.getCount());
        assertTrue(maxObject.getCount() > mbtiCount2.getCount());
        assertTrue(maxObject.getCount() > mbtiCount3.getCount());
        assertSame(maxObject.getMbti(), mbti);
    }

    @Test
    public void 득표수가_같은_MBTI가_두개이상일때(){

        //given
        Mbti mbti = testMbtiRepository.findAll().get(0);
        Job job = testJobRepository.createJob();
        //when

        MbtiCount mbtiMax1 = MbtiCount.builder()
                .mbti(mbti)
                .job(job)
                .build();

        MbtiCount mbtiMax2 = MbtiCount.builder()
                .mbti(mbti)
                .job(job)
                .build();

        IntStream.range(0, 4).forEach(i -> {
            mbtiMax1.updateCount(true);
            mbtiMax2.updateCount(true);
        });

        childMbtiCountRepository.save(mbtiMax1);
        childMbtiCountRepository.save(mbtiMax2);

        //then
        List<MbtiCount> maxList = childMbtiCountRepository.findMaxByJob(job);
        assertEquals(maxList.size(), 2);

    }

    @Test
    public void 득표수가_없을때(){

        //given
        Job job = testJobRepository.createJob();
        //when
        List<MbtiCount> resultList = childMbtiCountRepository.findMaxByJob(job);
        //then
        assertEquals(resultList.size(), 0);
    }

    @Test
    public void 직업_카운트_증가_테스트(){

        //given
        Mbti mbti = testMbtiRepository.findAll().get(0);
        Job job = testJobRepository.createJobWithMbti(mbti);

        //when
        childMbtiCountRepository.modifyJobCount(mbti, job, true);

        //then
        MbtiCount max = childMbtiCountRepository.findMaxByJob(job).get(0);
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
        MbtiCount max = childMbtiCountRepository.findMaxByPerson(person).get(0);
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
        IntStream.range(0, count)
                .forEach( i->
                        childMbtiCountRepository.modifyJobCount(mbti, job, true));

        //then
        MbtiCount max = childMbtiCountRepository.findMaxByJob(job).get(0);
        assertEquals(max.getCount(), count);
    }

    @Test
    public void 직업으로_목록_조회(){

        //given
        Mbti mbti = testMbtiRepository.findAll().get(0);
        Job job = testJobRepository.createJobWithMbti(mbti);

        int count = 10;
        //when
        IntStream.range(0, count)
                .forEach(
                        i-> childMbtiCountRepository.modifyJobCount(mbti, job, true));
        //then
        childMbtiCountRepository.removeAllByJob(job);
        List<MbtiCount> findJobs = childMbtiCountRepository.findAllByJob(job.getId());
        assertEquals(findJobs.size(), 0);
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
                .job(job).build();

        if(count > 0){
            IntStream.range(0, 1).forEach( i-> mbtiCount.updateCount(true));
        }

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
            super(em, new MbtiRepositoryImpl(em));
            thisEm = em;
        }

        public Long save(MbtiCount mbtiCount){
            thisEm.persist(mbtiCount);
            return mbtiCount.getId();
        }
    }


}