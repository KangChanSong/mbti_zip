package com.mbtizip.repository.mbtiCount;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.candidate.person.Person;

import java.util.List;

public interface MbtiCountRepository {
    // Job, Person 의 모든 mbti 득표수를 계산하기 위해 불러옴
    List<Object[]> findAllByCandidate(Long candidateId);

    // 최댓값 불러오는 메서드
    List<MbtiCount> findMaxByCandidate(Candidate candidate);

    // 카운트 증가, 감소 메서드
    void modifyCandidateCount(Mbti mbti , Candidate candidate, boolean isIncrease);

    void removeAllByCandidate(Candidate candidate);

    void insertAllByCandidate(Candidate candidate);

    Long sumAllOfCandidate(Long candidateId);
}
