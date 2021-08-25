package com.mbtizip.domain.person.dto;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.category.dto.CategoryRegisterDto;
import com.mbtizip.domain.person.Gender;
import com.mbtizip.domain.person.Person;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PersonRegisterDto {

    private String name;
    private String writer;
    private String gender;
    private String description;
    private String password;
    private List<Long> categoryIds;

    public Person toEntity(){
        return Person.builder()
                .name(name)
                .writer(writer)
                .gender(Gender.valueOf(gender))
                .description(description)
                .password(password)
                .build();
    }

    public void setGender(String gender) {
        if(!gender.equals("MALE") && !gender.equals("FEMALE")){
            throw new IllegalArgumentException("파라미터는 'MALE' 또는 'FEMALE' 이어야 합니다.");
        }
        this.gender = gender;
    }
}

