package com.mbtizip.repository.test;

import com.mbtizip.domain.category.Category;

import javax.persistence.EntityManager;

public class TestCategoryRepository {

    public static final String CATEGORY_NAME = "음악가";

    private final EntityManager em;

    public TestCategoryRepository(EntityManager em) {
        this.em = em;
    }

    public Category createCategory(){
        Category category = Category.builder()
                .name(CATEGORY_NAME)
                .build();

        em.persist(category);

        return category;
    }

    public Category createCategory(String name){
        Category category = Category.builder()
                .name(name)
                .build();

        em.persist(category);

        return category;
    }
}
