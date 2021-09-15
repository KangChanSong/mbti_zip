package com.mbtizip.service.mbtiCount;

import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.candidate.person.Person;

import java.util.List;

public interface MbtiCountService {
    void vote(Mbti mbti, Object objz);
    void cancelVote(Mbti mbti, Object obj);
    List<MbtiCount> getVotesByJob(Long jobId);
    List<MbtiCount> getVotesByPerson(Long personId);
    void deleteAllByPerson(Person person);
    void deleteAllByJob(Job job);

    void initializeByPerson(Person person);
    void initailizeByJob(Job job);

    Long getTotalCountOfJob(Long jobId);
    Long getTotalCountOfPerson(Long personId);
}
