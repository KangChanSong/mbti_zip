package com.mbtizip.service.mbtiCount;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.person.Person;

import java.util.List;
import java.util.Map;

public interface MbtiCountService {
    void vote(Mbti mbti, Object objz);
    void cancelVote(Mbti mbti, Object obj);
    Map<String, Integer> getVotesByJob(Job job);
    Map<String, Integer> getVotesByPerson(Person person);
    void deleteAllByPerson(Person person);
    void deleteAllByJob(Job job);
}
