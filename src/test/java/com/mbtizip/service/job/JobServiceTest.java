package com.mbtizip.service.job;

import com.mbtizip.domain.job.Job;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbtiCount.MbtiCountRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

class JobServiceTest {

    JobService jobService;

    @Mock
    JobRepository jobRepository;

    @Mock
    MbtiCountRepository mbtiCountRepository;



    @BeforeEach
    public void setUp(){
        jobService = new JobServiceImpl(jobRepository, mbtiCountRepository);
    }

    /**
     * 직업 등록 (MBTI 는 없음)
     */


    /**
     * 직업 MBTI와 함께 조회 
     */

    /**
     * 직업 목록 MBTI 와 함계 조회
     */

    /**
     * 직업 수정
     */

    /**
     * 직업 삭제
     */

    //== private 메서드 ==//

}