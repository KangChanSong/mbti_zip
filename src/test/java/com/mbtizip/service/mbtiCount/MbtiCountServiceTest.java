package com.mbtizip.service.mbtiCount;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.mbtiCount.MbtiCountRepository;
import com.mbtizip.repository.test.TestJobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MbtiCountServiceTest {

    MbtiCountService mbtiCountService;

    @Mock
    MbtiCountRepository mockMbtiCountRepository;

    @Mock
    JobRepository mockJobRepository;

    @Mock
    MbtiRepository mockMbtiRepository;

    /**
     * MbtiRepository.find()를 호출했을떄 첫번째는 INFP 두번쨰는 ENTP, 세번쨰는 INTJ 반환
     */
    @BeforeEach
    public void setUp(){
        mbtiCountService = new MbtiCountServiceImpl(mockMbtiCountRepository);

        // Mock Set Up
        when(mockJobRepository.find(anyLong())).thenReturn(Job.builder()
                        .title(TestJobRepository.JOB_TITLE)
                        .writer(TestJobRepository.JOB_WRITER)
                .build());

        when(mockMbtiRepository.find(anyLong()))
                .thenReturn(Mbti.builder().name(MbtiEnum.INFP).build())
                .thenReturn(Mbti.builder().name(MbtiEnum.ENTP).build())
                .thenReturn(Mbti.builder().name(MbtiEnum.INTJ).build());
    }

    /**
     * Mock 오브젝트가 잘 생성되는지에 대한 테스트
     */
    @Test
    public void MOCK_오브젝트_테스트(){

        //when
        String jobTitle = mockJobRepository.find(anyLong()).getTitle();
        MbtiEnum mbti1 = mockMbtiRepository.find(anyLong()).getName();
        MbtiEnum mbti2 = mockMbtiRepository.find(anyLong()).getName();
        //then
        assertEquals(jobTitle, TestJobRepository.JOB_TITLE);
        assertEquals(mbti1 , MbtiEnum.INFP);
        assertEquals(mbti2, MbtiEnum.ENTP);
    }

    /**
     * 직업 MBTI 별 투표수 조회 후
     *  득표율이 가장 높은 MBTI를 해당 직업의 MBTI 칼럼으로 수정
     */
    @Test
    public void 직업_MBTI별_투표(){

        //given
        Mbti infp =mockMbtiRepository.find(anyLong());
        Mbti entp = mockMbtiRepository.find(anyLong());
        Job job = mockJobRepository.find(anyLong());

        int infpCount = 10;
        int entpCount = 9;

        //when
        MbtiCount resultInfp = MbtiCount.builder()
                        .mbti(infp)
                        .job(job).build();

        when(mockMbtiCountRepository.findMaxByJob(job)).thenReturn(resultInfp);

        for(int i = 0 ; i < infpCount ; i++){
            mbtiCountService.vote(infp, job);
        }
        for(int i = 0 ; i < entpCount ; i++){
            mbtiCountService.vote(entp, job);
        }

        //then
        assertEquals(job.getMbti(), infp);
    }

    /**
     * 득표율이 없을때는 ?
     */
    @Test
    public void 득표울_0(){

        //given
        Job job = mockJobRepository.find(anyLong());

        //when

        //then
    }


    /**
     * 득표율이 같은 MBTI가 있을때는?
     */
}
