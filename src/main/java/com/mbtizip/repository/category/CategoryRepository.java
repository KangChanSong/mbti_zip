package com.mbtizip.repository.category;

import com.mbtizip.domain.category.Category;

public interface CategoryRepository {
    public Long save(Category category);
    public Category find(Long categoryId);
}
