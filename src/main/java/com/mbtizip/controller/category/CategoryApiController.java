package com.mbtizip.controller.category;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.category.dto.CategoryListDto;
import com.mbtizip.domain.category.dto.CategoryRegisterDto;
import com.mbtizip.domain.common.pageSortFilter.PageSortDto;
import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.service.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryApiController {

    private final CategoryService categoryService;

    //카테고리 등록
    @PostMapping("/register")
    public BooleanResponseDto register(@RequestBody CategoryRegisterDto dto){

        Boolean isSuccess = categoryService.register(dto.toEntity()) == null ? false : true;

        return new BooleanResponseDto(isSuccess);
    }

    //카테고리 목록 조회
    @GetMapping("/list")
    public CategoryListDto getList(){

        List<Category> findCategories = categoryService.findAll();
        return CategoryListDto.toDto(findCategories);
    }

}
