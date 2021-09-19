package com.mbtizip.repository.interaction;

import com.mbtizip.domain.interaction.Interaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class InteractionRepositoryImpl implements InteractionRepository {

    private final EntityManager em;

    @Override
    public Long save(Interaction interaction) {
        em.persist(interaction);
        return interaction.getId();
    }

    @Override
    public Interaction findOneByObject(Interaction interaction) {

        try {
            Interaction finded = (Interaction) em.createQuery("select i from Interaction i " +
                            "where i.dType =: dType" +
                            " and i.cookie =: cookie" +
                            " and ( i.personId =: personId or i.jobId =: jobId )")
                    .setParameter("dType", interaction.getDType())
                    .setParameter("cookie", interaction.getCookie())
                    .setParameter("personId", interaction.getPersonId())
                    .setParameter("jobId", interaction.getJobId())
                    .getSingleResult();

            return finded;

        } catch (RuntimeException e){
            return null;
        }
    }

    @Override
    public void remove(Interaction interaction) {
        em.remove(interaction);
    }
}
