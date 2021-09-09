package com.mbtizip.other;

import com.mbtizip.common.enums.TestJobEnum;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.job.QJob;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.mbti.QMbti;
import com.mbtizip.repository.test.TestJobRepository;
import com.mbtizip.repository.test.TestMbtiRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static com.mbtizip.common.enums.TestJobEnum.JOB_TITLE;
import static com.mbtizip.domain.mbti.MbtiEnum.INFP;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class QuerydslTest {

    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    ApplicationContext context;

    TestMbtiRepository testMbtiRepository;

    TestJobRepository testJobRepository;

    @BeforeEach
    public void setUp(){
        testMbtiRepository = new TestMbtiRepository(em);
        testJobRepository = new TestJobRepository(em);
    }

    @Test
    public void JpaQueryFactory_주입_테스트(){
        JPAQueryFactory bean = context.getBean(JPAQueryFactory.class);
        assertNotNull(bean);
    }

   // @Test
    public void 간단한_조회(){

        QMbti qMbti = QMbti.mbti;
        Mbti mbti = queryFactory.selectFrom(qMbti)
                .where(qMbti.name.eq(INFP))
                .fetchOne();

        assertEquals(mbti.getName(), INFP);

        Job job = testJobRepository.createJob();

        QJob qJob = QJob.job;
        Job findJob = queryFactory.selectFrom(qJob)
                .where(qJob.title.eq(JOB_TITLE.getText()))
                .fetchOne();

        assertEquals(findJob.getTitle(), JOB_TITLE.getText());
        assertEquals(findJob.getMbti(), null);
    }

    //@Test
    public void 조인(){

        //given
        Job infp = testJobRepository.createJobWithMbti(
                testMbtiRepository.findAll().get(0));
        Job entp = testJobRepository.createJobWithMbti(
                testMbtiRepository.findAll().get(1));

        QJob job = QJob.job;
        QMbti mbti = QMbti.mbti;
        //when
        List<Job> results = queryFactory.selectFrom(job)
                .innerJoin(job.mbti, mbti)
                .fetch();
        //then
        assertEquals(results.size(), 2);
    }

    //@Test
    public void 서브_쿼리(){

        //given
        Job job1 = testJobRepository.createJob();
        job1.modifyLikes(true);
        job1.modifyLikes(true);
        job1.modifyLikes(true);
        Job job2 = testJobRepository.createJob();
        job2.modifyLikes(true);
        job2.modifyLikes(true);
        Job job3 = testJobRepository.createJob();

        //when
        QJob job = QJob.job;
        Job findJob = queryFactory.selectFrom(job)
                .where(job.likes.eq(
                        JPAExpressions.select(job.likes.max()).from(job)
                )).fetchOne();
        //then
        assertEquals(findJob.getLikes(), 3);
    }

    //@Test
    public void 연관된_객체_프로퍼티로_검색() {

        //given
        List<Mbti> mbtis = testMbtiRepository.findAll();
        Job job1 = testJobRepository.createJobWithMbti(mbtis.get(0));
        Job job1_1 = testJobRepository.createJobWithMbti(mbtis.get(0));
        Job job1_2 = testJobRepository.createJobWithMbti(mbtis.get(0));
        Job job2 = testJobRepository.createJobWithMbti(mbtis.get(1));
        Job job3 = testJobRepository.createJobWithMbti(mbtis.get(2));

        //when
        QJob job = QJob.job;
        MbtiEnum filterName = mbtis.get(0).getName();

        BooleanExpression keyword = QMbti.mbti.name.eq(filterName);

        List<Job> findJob = joinQuery()
                .where(keyword)
                .offset(0)
                .limit(5)
                .fetch();

        System.out.println(findJob.get(0).getMbti().getName());
        assertEquals(findJob.get(0).getMbti().getName(), filterName);
        assertEquals(findJob.size(), 3);
        //then
    }

    private JPAQuery<Job> joinQuery() {
        QJob job = QJob.job;
        QMbti mbti = QMbti.mbti;
        return queryFactory.selectFrom(job)
                .leftJoin(job.mbti, mbti);
    }
}
