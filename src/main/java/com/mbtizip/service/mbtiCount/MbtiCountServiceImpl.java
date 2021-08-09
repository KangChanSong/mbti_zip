package com.mbtizip.service.mbtiCount;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.mbtiCount.MbtiCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MbtiCountServiceImpl implements MbtiCountService{

    private final MbtiCountRepository mbtiCountRepository;

    @Override
    public void vote(Mbti mbti, Job job) {
        mbtiCountRepository.modifyJobCount(mbti, job, true);

        MbtiCount max = mbtiCountRepository.findMaxByJob(job);

        Mbti resultMbti = max.getMbti();

        if(resultMbti != job.getMbti()){
            job.changeMbti(resultMbti);
        }
    }
}
