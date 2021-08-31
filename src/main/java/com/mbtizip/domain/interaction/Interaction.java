package com.mbtizip.domain.interaction;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Interaction {

    @Id @GeneratedValue
    @Column(name = "interaction_id")
    private Long id;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "d_type")
    private String dType;

    @Builder
    public Interaction(Long personId, Long jobId, String dType){
        this.personId = personId;
        this.jobId = jobId;

        if(!dType.equals("L") && !dType.equals("V")){
            throw new IllegalArgumentException("dType 은 L이나 V여야 합니다. dType : " + dType);
        }

        this.dType = dType;
    }
}
