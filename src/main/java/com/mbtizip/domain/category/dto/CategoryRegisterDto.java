package com.mbtizip.domain.category.dto;

import com.mbtizip.domain.category.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryRegisterDto {

    private String name;
    public Category toEntity(){
        return Category.builder()
                .name(name).build();
    }
}
