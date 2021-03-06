package com.mbtizip.domain.common.pageSortFilter;

import com.mbtizip.domain.candidate.job.QJob;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.candidate.person.Gender;
import com.mbtizip.domain.candidate.person.QPerson;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageSortFilterDto<T extends InterfaceForPageSortFilter> extends PageSortDto {
    private String filterBy;
    private String keyword;

    //검색
    public BooleanExpression toPersonKeyword() {

        if (checkIfKeywordNull()) return null;

        QPerson person = QPerson.person;

        if (filterBy.equals("mbti")) {
            return person.mbti.name.eq(MbtiEnum.valueOf(keyword));
        }
        if (filterBy.equals("name")) {
            return person.name.likeIgnoreCase("%" + keyword + "%");
        }
        if (filterBy.equals("gender")) {
            return person.gender.eq(Gender.valueOf(keyword));
        }
        if (filterBy.equals("category")){
            return person.category.name.eq(keyword);
        }
        return null;
    }
    public BooleanExpression toJobKeyword(){

        if(checkIfKeywordNull()) return null;

        QJob job = QJob.job;

        if(filterBy.equals("mbti")) {
            return job.mbti.name.eq(MbtiEnum.valueOf(keyword));
        }
        if(filterBy.equals("title")){
            return job.title.like("%" + keyword + "%");
        }
        return null;
    }

    //== private 메서드 ==//
    private Boolean checkIfKeywordNull() {
        if(filterBy == null || filterBy.equals("")){
            return true;
        }
        if( keyword == null || keyword.equals("")){
            return true;
        }
        return false;
    }
}
