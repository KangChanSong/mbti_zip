package com.mbtizip.repository;

import com.mbtizip.common.enums.TestCategoryEnum;
import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.pageSortFilter.Page;
import com.mbtizip.repository.category.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
@Transactional
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

   // @Test
    public void 카테고리_등록(){

        //given
        Category category = Category.builder()
                .name(TestCategoryEnum.CATEGORY_NAME_1.getText())
                .build();
        //when
        Long saveId = categoryRepository.save(category);
        //then
        Category findCategory = categoryRepository.find(saveId);
        assertEquals(findCategory.getName(), TestCategoryEnum.CATEGORY_NAME_1.getText());
    }

}
