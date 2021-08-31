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

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "d_type")
    private String dType;

    @Builder
    public Interaction(String sessionId, Long personId, Long jobId, String dType){
        this.sessionId = sessionId;
        this.personId = personId;
        this.jobId = jobId;
        this.dType = checkAndGetDType(dType);
    }

    private String checkAndGetDType(String dType){
        if(dType == null || dType.isEmpty()){
            throw new IllegalArgumentException("dType은 null일 수 없습니다.");
        }
        if(!dType.equals("L") && !dType.equals("V")){
            throw new IllegalArgumentException("dType 은 L이나 V여야 합니다. dType : " + dType);
        }
        return dType;
    }
}
