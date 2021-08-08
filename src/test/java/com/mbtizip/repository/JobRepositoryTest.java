package com.mbtizip.repository;

import com.mbtizip.domain.job.Job;
import com.mbtizip.exception.NoEntityFoundException;
import com.mbtizip.repository.job.JobRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class JobRepositoryTest {

    @Autowired
    private JobRepository jobRepository;
    
    @Test 
    public void 직업_등록(){
        //given
        String title = "개발자";
        Job job = Job.builder()
                .title(title)
                .build();
        
        //when
        Long saveId = jobRepository.save(job);
        
        //then
        Job findJob = jobRepository.find(saveId);

        assertEquals(findJob.getTitle(), title);
    }

    @Test
    public void 조회_실패_예외(){
        //then

        assertThrows(NoEntityFoundException.class, () -> {
           jobRepository.find(123L);
        });
    }

}