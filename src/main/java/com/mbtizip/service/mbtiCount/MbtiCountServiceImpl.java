package com.mbtizip.service.mbtiCount;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.person.Person;
import com.mbtizip.repository.mbti.MbtiRepository;
import com.mbtizip.repository.mbtiCount.MbtiCountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MbtiCountServiceImpl implements MbtiCountService{

    private final MbtiCountRepository mbtiCountRepository;
    private final MbtiRepository mbtiRepository;

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
    public Map<String, Integer> getVotesByJob(Job job) {
        return getVotesByObject(job);
    }

    @Override
    public Map<String, Integer> getVotesByPerson(Person person) {
        return getVotesByObject(person);
    }

    @Override
    public void deleteAllByPerson(Person person) {
        mbtiCountRepository.removeAllByPerson(person);
    }

    @Override
    public void deleteAllByJob(Job job) {
        mbtiCountRepository.removeAllByJob(job);
    }

    //== private method ==//

    private Map<String , Integer> getVotesByObject(Object obj){
        checkInstance(obj);
        Map<String, Integer> map = new HashMap<>();
        List<MbtiCount> findCounts = new ArrayList<>();
        if(obj instanceof Job) {
            findCounts = mbtiCountRepository.findAllByJob((Job) obj);
        } else if (obj instanceof Person){
            findCounts = mbtiCountRepository.findAllByPerson((Person) obj);
        }
        findCounts.forEach( i->
                map.put(i.getMbti().getName().getText(),i.getCount()));
        return map;
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
        log.info("findMaxByJob call");
        updateObjecMbti(maxVoted, job.getMbti(), job::changeMbti);
    }

    private void updatePersonMbti(Person person){
        List<MbtiCount> maxVoted = mbtiCountRepository.findMaxByPerson(person);
        updateObjecMbti(maxVoted, person.getMbti(), person::changeMbti);
    }

    private void updateObjecMbti(List<MbtiCount> maxVoted, Mbti originalMbti, Consumer<Mbti> consumer){

        if(maxVoted.size() > 1){
            consumer.accept(mbtiRepository.findByName(MbtiEnum.DRAW));
        } else if (maxVoted.size() == 1){

            Mbti resultMbti = maxVoted.get(0).getMbti();
            if(resultMbti != originalMbti){
                consumer.accept(resultMbti);
            }
        } else {
            consumer.accept(mbtiRepository.findByName(MbtiEnum.NONE));
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
