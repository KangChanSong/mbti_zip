package com.mbtizip.repository.interaction;

import com.mbtizip.domain.interaction.Interaction;

public interface InteractionRepository {
    Long save(Interaction interaction);
    Interaction findOneByObject(Interaction interaction);
    void remove(Interaction interaction);
}
