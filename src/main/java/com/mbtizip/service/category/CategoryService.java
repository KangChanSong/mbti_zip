package com.mbtizip.service.category;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.pageSortFilter.Page;

import java.util.List;

public interface CategoryService {
    Long register(Category category);
    List<Category> findAll(Page page);
}
