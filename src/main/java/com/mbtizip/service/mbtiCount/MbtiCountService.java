package com.mbtizip.service.mbtiCount;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.candidate.person.Person;

import java.util.List;

public interface MbtiCountService {
    void vote(Mbti mbti, Candidate candidate);
    void cancelVote(Mbti mbti, Candidate candidate);
    List<MbtiCount> getVotesByCandidate(Long candidateId);
    void deleteAllByCandidate(Candidate candidate);

    void initializeByCandidate(Candidate candidate);
    Long getTotalCountOfCandidate(Long candidateId);
}
