package com.mbtizip.service.interaction;

import com.mbtizip.domain.interaction.Interaction;

public interface InteractionService {
    boolean checkIfExists(Interaction interaction);
    boolean checkAndRemove(Interaction interaction);
}
