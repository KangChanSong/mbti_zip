package com.mbtizip.repository.mbtiCount;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.person.Person;

import java.util.List;

public interface MbtiCountRepository {
    // Job, Person 의 모든 mbti 득표수를 계산하기 위해 불러옴
    List<MbtiCount> findAllByJob(Long jobId);
    List<MbtiCount> findAllByPerson(Long personId);

    // 최댓값 불러오는 메서드
    List<MbtiCount> findMaxByJob(Job job);
    List<MbtiCount> findMaxByPerson(Person person);

    // 카운트 증가, 감소 메서드
    void modifyJobCount(Mbti mbti , Job job, boolean isIncrease);
    void modifyPersonCount(Mbti mbti , Person person, boolean isIncrease);

    void removeAllByPerson(Person person);
    void removeAllByJob(Job job);

    void insertAllByPerson(Person person);
    void insertAllByJob(Job job);

    Long sumAllOfJob(Long jobId);
    Long sumAllOfPerson(Long personId);
}
