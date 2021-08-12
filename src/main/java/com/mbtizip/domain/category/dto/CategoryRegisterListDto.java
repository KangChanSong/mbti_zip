package com.mbtizip.domain.category.dto;

import com.mbtizip.domain.category.Category;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryRegisterListDto {

    private List<CategoryRegisterDto> dtoList;

    public List<Category> toEntityList(){
        List<Category> categories = new ArrayList<>();
        dtoList.forEach( dto -> categories.add(dto.toEntity()));
        return categories;
    }
}
