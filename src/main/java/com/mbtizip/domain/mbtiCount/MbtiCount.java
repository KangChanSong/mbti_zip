package com.mbtizip.domain.mbtiCount;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.person.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class MbtiCount {

    @Id @GeneratedValue
    @Column(name = "mbti_count_id")
    private Long id;

    @Column(columnDefinition = "integer default 0")
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mbti_id")
    private Mbti mbti;

    /**
     * Job 과 Person 은 둘중 하나만 있어야 함.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    //== 카운트 관련 메서드 ==//
    public void updateCount(boolean isIncrease){

        if(isIncrease){
            this.count++;
        } else {
            if(this.count > 0){
                this.count--;
            } else {
                throw new IllegalArgumentException("count = 0 에서 더이상 감소할 수 없습니다.");
            }
        }
    }
}
