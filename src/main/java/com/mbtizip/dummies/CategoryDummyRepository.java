package com.mbtizip.dummies;

import com.mbtizip.domain.category.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CategoryDummyRepository {

    private final EntityManager em;

    @Transactional
    public void save(String name){
        em.persist(Category.builder()
                .name(name
                ).build());
    }
}
