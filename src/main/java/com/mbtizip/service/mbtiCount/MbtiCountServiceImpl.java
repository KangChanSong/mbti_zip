package com.mbtizip.service.mbtiCount;

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
    public void vote(Mbti mbti, Object obj) {
        checkMbti(mbti);
        checkInstance(obj);
        commonVote(mbti, obj, true);
    }

    @Transactional
    @Override
    public void cancelVote(Mbti mbti, Object obj) {
        checkMbti(mbti);
        checkInstance(obj);
        commonVote(mbti, obj, false);
    }

    @Override
    public List<MbtiCount> getVotesByJob(Long jobId) {
        return getVotesByObject(jobId, "job");
    }

    @Override
    public List<MbtiCount> getVotesByPerson(Long personId) {
        return getVotesByObject(personId, "person");
    }

    @Transactional
    @Override
    public void deleteAllByPerson(Person person) {
        mbtiCountRepository.removeAllByPerson(person);
    }

    @Transactional
    @Override
    public void deleteAllByJob(Job job) {
        mbtiCountRepository.removeAllByJob(job);
    }

    @Override
    public void initializeByPerson(Person person) {
        mbtiCountRepository.insertAllByPerson(person);
    }

    @Override
    public void initailizeByJob(Job job) {
        mbtiCountRepository.insertAllByJob(job);
    }

    @Override
    public Long getTotalCountOfJob(Long jobId) {
        return mbtiCountRepository.sumAllOfJob(jobId);
    }

    @Override
    public Long getTotalCountOfPerson(Long personId) {
        return mbtiCountRepository.sumAllOfPerson(personId);
    }

    //== private method ==//

    private List<MbtiCount> getVotesByObject(Long targetId, String target){

        List<Object[]> result;

        if(target.equals("job")) {
            result = mbtiCountRepository.findAllByJob(targetId);
        } else if (target.equals("person")){
            result = mbtiCountRepository.findAllByPerson(targetId);
        } else {
            throw new IllegalArgumentException("target 이 적합하지 않습니다. target : " + target);
        }
        return result.stream().map(res -> {
                    MbtiCount mbtiCount = (MbtiCount) res[0];
                    Mbti mbti = (Mbti) res[1];
                    mbtiCount.setMbti(mbti);
                    return mbtiCount;
                    }).collect(Collectors.toList());
    }

    // 투표, 표 취소에 공유되는 공통 메서드
    private void commonVote(Mbti mbti, Object obj , Boolean isIncrease){
        if(obj instanceof Job){
            Job jobObj = (Job) obj;
            mbtiCountRepository.modifyJobCount(mbti, jobObj, isIncrease);
            updateJobMbti(jobObj);
        } else if (obj instanceof Person){
            Person personObj = (Person) obj;
            mbtiCountRepository.modifyPersonCount(mbti, personObj, isIncrease);
            updatePersonMbti(personObj);
        }
    }

    // 득표수에 따라 MBTI 가 반영되는 메서드
    private void updateJobMbti(Job job){
        List<MbtiCount> maxVoted = mbtiCountRepository.findMaxByJob(job);
        updateObjecMbti(maxVoted, job.getMbti(), job::changeMbti);
    }

    private void updatePersonMbti(Person person){
        List<MbtiCount> maxVoted = mbtiCountRepository.findMaxByPerson(person);
        updateObjecMbti(maxVoted, person.getMbti(), person::changeMbti);
    }

    private void updateObjecMbti(List<MbtiCount> maxVoted, Mbti originalMbti, Consumer<Mbti> consumer){

        if(maxVoted != null && maxVoted.get(0).getCount() == 0) consumer.accept(null);

        // 같은 투표수의 MBTI 가 두개 이상 존재할때
        if(maxVoted.size() > 1){
            consumer.accept(null);
        } else if (maxVoted.size() == 1){
            Mbti resultMbti = maxVoted.get(0).getMbti();
            if(resultMbti != originalMbti){
                consumer.accept(resultMbti);
            }
        } else {
            consumer.accept(null);
        }
    }

    private void checkMbti(Mbti wrongMbti){
        if(wrongMbti.getName().equals(MbtiEnum.DRAW) || wrongMbti.getName().equals(MbtiEnum.NONE)){
            throw new IllegalArgumentException("DRAW 나 NONE 에게 투표할 수 없습니다.");
        }
    }

    private void checkInstance(Object obj){
        if(obj instanceof Job == false && obj instanceof Person == false){
            throw new IllegalArgumentException("Job 이나 Person 의 인스턴스여야 합니다");
        }
    }
}
