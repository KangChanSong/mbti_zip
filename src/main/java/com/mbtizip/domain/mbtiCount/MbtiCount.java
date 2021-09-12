package com.mbtizip.domain.mbtiCount;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.person.Person;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class MbtiCount {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mbti_count_id")
    private Long id;

    @Column(columnDefinition = "integer default 0")
    private int count;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mbti_id")
    private Mbti mbti;

    /**
     * Job 과 Person 은 둘중 하나만 있어야 함.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @Builder
    public MbtiCount(Mbti mbti , Job job, Person person){
        this.mbti = mbti;
        this.person = person;
        this.job = job;
    }

    public void setMbti(Mbti mbti) {
        this.mbti = mbti;
    }

    //== 카운트 관련 메서드 ==//
    public void updateCount(boolean isIncrease){

        if(isIncrease){
            this.count++;
        } else {
            if(this.count > 0){
                this.count--;
            }
        }
    }
}
