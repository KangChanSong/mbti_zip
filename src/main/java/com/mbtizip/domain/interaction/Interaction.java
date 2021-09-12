package com.mbtizip.domain.interaction;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.persistence.*;

import static com.mbtizip.controller.common.common.InteractionControllerHelper.TARGET_INVALID_ERROR_MESSAGE;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Interaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interaction_id")
    private Long id;

    @NotNull
    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "person_id")
    private Long personId;

    @Column(name = "job_id")
    private Long jobId;

    @NotNull
    @Column(name = "d_type")
    private String dType;

    /**
     * @param target = Job or Person or Comment
     * @param targetId = primary key
     * @param dType = L for like or V for vote
     */
    public Interaction(String target, Long targetId , String dType){
        assignTarget(target, targetId);
        assignDType(dType);
        this.sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
    }

    private void assignTarget(String target, Long targetId) {
        if(target.equals("person")) this.personId = targetId;
        else if(target.equals("job")) this.jobId = targetId;
        else throw new IllegalArgumentException(TARGET_INVALID_ERROR_MESSAGE + target);
    }

    private void assignDType(String dType){
        if(dType == null || dType.isEmpty()){
            throw new IllegalArgumentException("dType은 null일 수 없습니다.");
        }
        if(!dType.equals("L") && !dType.equals("V")){
            throw new IllegalArgumentException("dType 은 L이나 V여야 합니다. dType : " + dType);
        }
        this.dType =dType;
    }
}
