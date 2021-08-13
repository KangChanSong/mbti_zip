package com.mbtizip.domain.category.dto;

import com.mbtizip.domain.category.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class CategoryListDto {

    private List<CategoryGetDto> categoryGetDtos;

    public static CategoryListDto toDto(List<Category> categories){
        if(categories == null || categories.size() == 0) throw new IllegalArgumentException("조회된 카테고리가 없습니다.");
        return CategoryListDto.builder()
                .categoryGetDtos(
                        categories.stream().map(category -> CategoryGetDto.toDto(category))
                                .collect(Collectors.toList())
                )
                .build();
    }
}
