package com.mbtizip.domain.category.dto;

import com.mbtizip.domain.category.Category;

public class CategoryRegisterDto {

    private String name;
    public Category toEntity(){
        return Category.builder()
                .name(name).build();
    }
}
