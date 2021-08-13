package com.mbtizip.domain.category.dto;

import com.mbtizip.domain.category.Category;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CategoryGetDto {
    private Long id;
    private String name;

    public static CategoryGetDto toDto(Category category){
        return CategoryGetDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
