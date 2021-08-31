package com.mbtizip.repository.interaction;

import com.mbtizip.domain.interaction.Interaction;

public interface InteractionRepository {
    Long save(Interaction interaction);
    Long countByCondition(Long personId, Long jobId, String dType);
    int remove(Interaction interaction);
}
