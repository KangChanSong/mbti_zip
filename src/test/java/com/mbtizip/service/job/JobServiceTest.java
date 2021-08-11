package com.mbtizip.service.job;

import com.mbtizip.domain.job.Job;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbtiCount.MbtiCountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;

import static com.mbtizip.common.util.TestEntityGenerator.*;

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

    @DisplayName("직업 등록 테스트")
    public void 직업_등록(){

        //given
        Job job = createJob();

        //when
        jobService.register(job);

        //then
    }

    @DisplayName("직업 정렬, 검색, 페이징 테스트")
    public void 직업_목록_조회(){

        //given

        //when
        //jobService.findAll();
        //then
    }

    //== private 메서드 ==//
    private void insertMultipleJobs(){

    }

}