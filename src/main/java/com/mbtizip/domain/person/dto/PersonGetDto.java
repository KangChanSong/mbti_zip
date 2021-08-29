package com.mbtizip.domain.person.dto;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.person.Person;
import com.mbtizip.domain.personCategory.PersonCategory;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonGetDto {

    private Long id;
    private String name;
    private String description;
    private String gender;
    private int likes;
    private int views;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private List<String> categories;
    private String mbti;

    public static PersonGetDto toDto(Person person, List<Category> categories){

        return PersonGetDto.builder()
                .id(person.getId())
                .name(person.getName())
                .description(person.getDescription())
                .gender(person.getGender().getText())
                .likes(person.getLikes())
                .views(person.getViews())

                .createDate(person.getCreateDate())
                .updateDate(person.getUpdateDate())

                .mbti(validateAndReturnMbti(person))
                .categories(extractCategoryNames(categories))
                .build();
    }

    private static String validateAndReturnMbti(Person person){
        if(person.getMbti() == null){
            return "MBTI 미정";
        } else {
            return person.getMbti().getName().getText().toUpperCase(Locale.ROOT);
        }
    }

    private static List<String> extractCategoryNames(List<Category> categories){

        List<String> names = new ArrayList<>();
        if(categories != null && categories.size() != 0) {
            categories.forEach(category -> names.add(category.getName()));
        }
        return names;
    }

}
