package com.mbtizip.repository.mbtiCount;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.person.Person;

import java.util.List;

public interface MbtiCountRepository {
    Long save(MbtiCount mbtiCount);
    MbtiCount find(Long id);
    // Job, Person 의 모든 mbti 득표수를 계산하기 위해 불러옴
    List<MbtiCount> findAllByJob(Long jobId);
    List<MbtiCount> findAllByPerson(Long personId);
}
