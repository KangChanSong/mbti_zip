package com.mbtizip.service.mbtiCount;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;

public interface MbtiCountService {
    void vote(Mbti infp, Job job);
}
