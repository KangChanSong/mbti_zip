package com.mbtizip.repository.category;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.exception.NoEntityFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
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

    @Override
    public List<Category> findAll(Page page) {
        return em.createQuery("select c from Category c")
                .setFirstResult(page.getOffset())
                .setMaxResults(page.getAmount())
                .getResultList();
    }
}
