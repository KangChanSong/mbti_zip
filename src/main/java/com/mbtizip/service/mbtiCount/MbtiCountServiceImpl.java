package com.mbtizip.service.mbtiCount;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.candidate.person.Person;
import com.mbtizip.repository.mbtiCount.MbtiCountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MbtiCountServiceImpl implements MbtiCountService{

    private final MbtiCountRepository mbtiCountRepository;

    @Transactional
    @Override
    public void vote(Mbti mbti, Candidate candidate) {
        checkMbti(mbti);
        executeVote(mbti, candidate, true);
    }

    @Transactional
    @Override
    public void cancelVote(Mbti mbti, Candidate candidate) {
        checkMbti(mbti);
        executeVote(mbti, candidate, false);
    }

    @Override
    public List<MbtiCount> getVotesByCandidate(Long candidateId) {
        List<Object[]> result = mbtiCountRepository.findAllByCandidate(candidateId);

        return result.stream().map(res -> {
                    MbtiCount mbtiCount = (MbtiCount) res[0];
                    Mbti mbti = (Mbti) res[1];
                    mbtiCount.setMbti(mbti);
                    return mbtiCount;
                }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteAllByCandidate(Candidate candidate) {
        mbtiCountRepository.removeAllByCandidate(candidate);
    }

    @Override
    public void initializeByCandidate(Candidate candidate) {
        mbtiCountRepository.insertAllByCandidate(candidate);
    }

    @Override
    public Long getTotalCountOfCandidate(Long candidateId) {
        return mbtiCountRepository.sumAllOfCandidate(candidateId);
    }
    //== private method ==//
    private void executeVote(Mbti mbti, Candidate candidate , Boolean isIncrease){
        List<MbtiCount> maxVoted = mbtiCountRepository.findMaxByCandidate(candidate);
        mbtiCountRepository.modifyCandidateCount(mbti, candidate, isIncrease);
        updateCandidateMbti(maxVoted, candidate);
    }

    // 득표수에 따라 MBTI 가 반영되는 메서드
    private void updateCandidateMbti(List<MbtiCount> maxVoted, Candidate candidate){

        if(maxVoted != null && maxVoted.get(0).getCount() == 0) candidate.changeMbti(null);

        // 같은 투표수의 MBTI 가 두개 이상 존재할때
        if(maxVoted.size() > 1){
            candidate.changeMbti(null);
        } else if (maxVoted.size() == 1){
            Mbti resultMbti = maxVoted.get(0).getMbti();
            if(resultMbti != candidate.getMbti()){
                candidate.changeMbti(resultMbti);
            }
        } else {
            candidate.changeMbti(null);
        }
    }

    private void checkMbti(Mbti wrongMbti){
        if(wrongMbti.getName().equals(MbtiEnum.DRAW) || wrongMbti.getName().equals(MbtiEnum.NONE)){
            throw new IllegalArgumentException("DRAW 나 NONE 에게 투표할 수 없습니다.");
        }
    }

}
