package com.mbtizip.domain.common;

import com.mbtizip.domain.job.QJob;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.person.Gender;
import com.mbtizip.domain.person.QPerson;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageSortFilterDto<T extends InterfaceForPageSortFilter> extends PageSortDto {

    private String filterBy;
    private String keyword;

    //검색
    public BooleanExpression toPersonKeyword(){

        checkIfKeywordNull();

        QPerson person = QPerson.person;

        if(filterBy.equals("mbti")) {
            return person.mbti.name.eq(MbtiEnum.valueOf(keyword));
        }
        if(filterBy.equals("name")){
            return person.name.eq(keyword);
        }
        if(filterBy.equals("gender")){
            return person.gender.eq(Gender.valueOf(keyword));
        }

        return null;
    }
    public BooleanExpression toJobKeyword(){

        checkIfKeywordNull();

        QJob job = QJob.job;

        if(filterBy.equals("mbti")) {
            return job.mbti.name.eq(MbtiEnum.valueOf(keyword));
        }
        if(filterBy.equals("title")){
            return job.title.eq(keyword);
        }
        return null;
    }

    //== private 메서드 ==//
    private void checkIfKeywordNull() {
        if(filterBy == null || filterBy.equals("")){
            throw new IllegalArgumentException("filterBy 가 null 입니다.");
        }
        if( keyword == null || keyword.equals("")){
            throw new IllegalArgumentException("keyword 가 null 입니다.");
        }
    }
}
