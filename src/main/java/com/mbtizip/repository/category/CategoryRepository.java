package com.mbtizip.repository.category;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.pageSortFilter.Page;

import java.util.List;

public interface CategoryRepository {
    Long save(Category category);
    Category find(Long categoryId);
    List<Category> findAll(Page page);
}
