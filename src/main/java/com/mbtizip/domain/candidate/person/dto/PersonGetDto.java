package com.mbtizip.domain.candidate.person.dto;

import com.mbtizip.domain.common.var.Text;
import com.mbtizip.domain.candidate.person.Person;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Locale;

import static com.mbtizip.domain.common.FileNameProvider.getFileName;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonGetDto {

    private Long id;
    private String name;
    private String writer;
    private String description;
    private String gender;
    private int likes;
    private int views;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private String category;
    private String mbti;

    private String filename;

    public static PersonGetDto toDto(Person person){

        return PersonGetDto.builder()
                .id(person.getId())
                .name(person.getName())
                .writer(person.getWriter())
                .description(person.getDescription())
                .gender(person.getGender().getText())
                .likes(person.getLikes())
                .views(person.getViews())

                .createDate(person.getCreateDate())
                .updateDate(person.getUpdateDate())

                .mbti(validateAndReturnMbti(person))
                .category(person.getCategory().getName())

                .filename(getFileName(person.getFile()))
                .build();
    }

    private static String validateAndReturnMbti(Person person){
        if(person.getMbti() == null){
            return Text.NO_MBTI_VOTED;
        } else {
            return person.getMbti().getName().getText().toUpperCase(Locale.ROOT);
        }
    }

}
