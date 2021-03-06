package com.mbtizip.service.job;

import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.repository.candidate.CandidateRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.service.mbtiCount.MbtiCountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;

import static com.mbtizip.common.util.TestEntityGenerator.createJob;

class JobServiceTest {

    JobService jobService;

    @Mock
    CandidateRepository candidateRepository;

    @Mock
    MbtiRepository mbtiRepository;

    @Mock
    MbtiCountService mbtiCountService;

    @BeforeEach
    public void setUp(){
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