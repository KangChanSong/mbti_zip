package com.mbtizip.service.mbtiCount;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.repository.mbtiCount.MbtiCountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MbtiCountServiceTest {

    MbtiCountService mbtiCountService;

    @Mock
    MbtiCountRepository mbtiCountRepository;

    @BeforeEach
    public void setUp(){
        mbtiCountService = new MbtiCountServiceImpl(mbtiCountRepository);
    }

    /**
     * 직업 MBTI 별 투표수 조회 후
     *  득표율이 가장 높은 MBTI를 해당 직업의 MBTI 칼럼으로 수정
     */
    @Test
    public void 직업_MBTI별_투표(){

        //given

        Mbti infp = Mbti.builder().build();
        Mbti entp = Mbti.builder().build();
        Job job = Job.builder().build();

        int infpCount = 10;
        int entpCount = 9;

        //when
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
     * 직업 별 MBTI 투표 후
     * 표 수가 증가했는지 확인
     */

    /**
     * 직업 별 MBTI 투표 취소 후
     * 표 수가 감소했는지 확인
     */

    /**
     * 득표율이 없을때는 ?
     */

    /**
     * 득표율이 같은 MBTI가 있을때는?
     */

    
}
