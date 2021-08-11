package com.mbtizip.repository.category;

import com.mbtizip.domain.category.Category;
import com.mbtizip.exception.NoEntityFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepository{

    private final EntityManager em;

    @Override
    public Long save(Category category) {
        em.persist(category);
        return category.getId();
    }

    @Override
    public Category find(Long categoryId) {
        Category category = Optional.of(em.find(Category.class, categoryId)).orElseThrow(
                () -> new NoEntityFoundException("해당 카테고리가 없습니다. id : " + categoryId)
        );
        return category;
    }
}
