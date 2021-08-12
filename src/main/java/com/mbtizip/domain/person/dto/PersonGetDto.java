package com.mbtizip.domain.person.dto;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.personCategory.PersonCategory;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonGetDto {

    private String name;
    private String description;
    private String gender;
    private int likes;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private List<String> categories;
    private String mbti;

    public static PersonGetDto toDto(Person person){

        return PersonGetDto.builder()
                .name(person.getName())
                .description(person.getDescription())
                .gender(person.getGender().getText())
                .likes(person.getLikes())

                .createDate(person.getCreateDate())
                .updateDate(person.getUpdateDate())

                .mbti(validateAndReturnMbti(person))
                .categories(extractCategoryNames(person))
                .build();
    }

    private static String validateAndReturnMbti(Person person){
        if(person.getMbti() == null){
            return null;
        } else {
            return person.getMbti().getName().getText();
        }
    }

    private static List<String> extractCategoryNames(Person person){
        List<String> categories = new ArrayList<>();
        person.getPersonCategories().forEach(
                personCategory -> {
                    String categoryName = personCategory.getCategory().getName();
                    categories.add(categoryName);
                }
        );

        return categories;
    }

}
