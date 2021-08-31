package com.mbtizip.controller.common.common;

import com.mbtizip.domain.interaction.Interaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;

@Slf4j
public class InteractionControllerHelper {

    public static final String TARGET_INVALID_ERROR_MESSAGE = "target 파라미터가 적합하지 않습니다. target = ";
    public static final Interaction buildInteraction(String target, Long targetId, String dType) {

        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        log.info("sessionId ========> " + sessionId);
        Interaction.InteractionBuilder builder = Interaction.builder()
                .sessionId(sessionId)
                .dType(dType);

        if(target.equals("person")){
            builder.personId(targetId);
        } else if(target.equals("job")){
            builder.jobId(targetId);
        } else {
            throw new IllegalArgumentException(TARGET_INVALID_ERROR_MESSAGE + target);
        }

        return builder.build();
    }
}
