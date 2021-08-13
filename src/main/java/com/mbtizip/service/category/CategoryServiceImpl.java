package com.mbtizip.service.category;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Long register(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll(Page page) {
        return categoryRepository.findAll(page);
    }
}
