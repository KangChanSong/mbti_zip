package com.mbtizip.repository.mbti;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbtiCount.MbtiCount;

public interface MbtiRepository {
    Long save(Mbti job);
    Mbti find(Long id);
}
