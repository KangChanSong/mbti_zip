package com.mbtizip.service.job;

import com.mbtizip.domain.job.Job;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.mbtiCount.MbtiCountRepository;
import com.mbtizip.service.mbtiCount.MbtiCountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;

import static com.mbtizip.common.util.TestEntityGenerator.*;

class JobServiceTest {

    JobService jobService;

    @Mock
    JobRepository jobRepository;

    @Mock
    MbtiRepository mbtiRepository;

    @Mock
    MbtiCountService mbtiCountService;

    @BeforeEach
    public void setUp(){
        jobService = new JobServiceImpl(jobRepository, mbtiRepository, mbtiCountService);
    }

    @DisplayName("직업 등록 테스트")
    public void 직업_등록(){

        //given
        Job job = createJob();

        //when
        jobService.register(job);

        //then
    }

    //== private 메서드 ==//
    private void insertMultipleJobs(){

    }

}