package com.mbtizip.service.interaction;

import com.mbtizip.domain.interaction.Interaction;
import com.mbtizip.repository.interaction.InteractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InteractionSeviceImpl implements InteractionService{
    private final InteractionRepository interactionRepository;

    @Transactional
    @Override
    public boolean checkIfExists(Interaction interaction) {
        Interaction finded = interactionRepository.findOneByObject(interaction);
        if(finded == null){
            interactionRepository.save(interaction);
            return false;
        } else {
            interactionRepository.remove(finded);
            return true;
        }
    }
}
