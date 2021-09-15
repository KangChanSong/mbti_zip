package com.mbtizip.domain.candidate.person.dto;

import com.mbtizip.domain.candidate.person.Gender;
import com.mbtizip.domain.candidate.person.Person;
import lombok.Data;

@Data
public class PersonRegisterDto {

    private String name;
    private String writer;
    private String gender;
    private String description;
    private String password;
    private Long categoryId;
    private String filename;

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

