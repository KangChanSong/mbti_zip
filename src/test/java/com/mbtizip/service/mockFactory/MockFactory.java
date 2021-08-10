package com.mbtizip.service.mockFactory;

import com.mbtizip.common.enums.TestJobEnum;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.repository.job.JobRepository;
import com.mbtizip.repository.mbti.MbtiRepository;
import org.mockito.Mock;
import org.mockito.Mockito;

import static com.mbtizip.domain.mbti.MbtiEnum.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * 서비스단 테스트를 위한 목 오브젝트와 그 메서드들을 테스트클래스끼리 공유하기 위한 클래스
 */
public class MockFactory {

    public static JobRepository createMockJobRepository(){
        JobRepository mock = Mockito.mock(JobRepository.class);

        String jobTitle = TestJobEnum.JOB_TITLE.getText();
        String jobWriter = TestJobEnum.JOB_WRITER.getText();

        when(mock.find(anyLong())).thenReturn(Job.builder()
                .title(jobTitle)
                .writer(jobWriter)
                .build());

        return mock;
    }

    public static MbtiRepository createMbtiJobRepository(){
        MbtiRepository mock = Mockito.mock(MbtiRepository.class);

        Mbti infp = Mbti.builder().name(INFP).build();
        Mbti entp = Mbti.builder().name(ENTP).build();
        Mbti intj = Mbti.builder().name(INTJ).build();

        when(mock.find(anyLong()))
                .thenReturn(infp)
                .thenReturn(entp)
                .thenReturn(intj);

        return mock;
    }
}
