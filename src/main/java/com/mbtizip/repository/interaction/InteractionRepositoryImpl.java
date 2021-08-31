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
    public Long countByCondition(Long personId, Long jobId, String dType) {
        return (Long) em.createQuery("select count(i) from Interaction i " +
                "where i.dType =: dType" +
                " and ( i.personId =: personId or i.jobId =: jobId )")
                .setParameter("dType", dType)
                .setParameter("personId", personId)
                .setParameter("jobId", jobId)
                .getSingleResult();
    }

    @Override
    public int remove(Interaction interaction) {
        return 0;
    }
}
