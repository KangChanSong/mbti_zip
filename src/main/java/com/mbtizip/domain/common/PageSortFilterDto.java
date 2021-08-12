package com.mbtizip.domain.common;

import com.mbtizip.domain.comment.QComment;
import com.mbtizip.domain.job.QJob;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.mbti.QMbti;
import com.mbtizip.domain.person.Gender;
import com.mbtizip.domain.person.QPerson;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageSortFilterDto<T extends InterfaceForPageSortFilter> {
    private int page = 1;
    private int size = 10;
    private String sort = "createDate";
    private String dir = "desc";
    private String filterBy;
    private String keyword;

    public void setDir(String dir) {
        if(!dir.equals("asc") && !dir.equals("desc")){
            this.dir = "desc";
        } else {
            this.dir = dir;
        }
    }

    public Page toPage(){
        return Page.builder()
                .pageNum(page)
                .amount(size)
                .build();
    }
    // 정렬
    public OrderSpecifier toPersonSort(){
        return createSort(QPerson.person);
    }
    public OrderSpecifier toJobSort(){
        return createSort(QJob.job);
    }
    
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

    private OrderSpecifier createSort(EntityPath qObject){

        OrderSpecifier orderSpecifier = null;

        DateTimePath createDatePath = makeCreateDatePath(qObject);
        DateTimePath updateDatePath = makeUpdateDatePath(qObject);
        NumberPath likesPath = makeLikesPath(qObject);

        if(dir.equals("desc")){
            switch (sort){
                case "createDate":
                    orderSpecifier = createDatePath.desc();
                    break;
                case "updateDate":
                    orderSpecifier = updateDatePath.desc();
                    break;
                case "likes":
                    orderSpecifier = likesPath.desc();
                    break;
            }
        }
        if(dir.equals("asc")) {
            switch (sort) {
                case "createDate":
                    orderSpecifier = createDatePath.asc();
                    break;
                case "updateDate":
                    orderSpecifier = updateDatePath.asc();
                    break;
                case "likes":
                    orderSpecifier = likesPath.asc();
                    break;
            }
        }

        return orderSpecifier;
    }



    private DateTimePath makeCreateDatePath(EntityPath qObject){

        DateTimePath createDatePath = null;

        if(qObject instanceof QPerson){
            createDatePath = QPerson.person.createDate;
        } else if (qObject instanceof QJob){
            createDatePath = QJob.job.createDate;
        } else if (qObject instanceof QComment){
            createDatePath = QComment.comment.createDate;
        }

        return createDatePath;
    }

    private DateTimePath makeUpdateDatePath(EntityPath qObject){

        DateTimePath updateDatePath = null;

        if(qObject instanceof QPerson){
            updateDatePath = QPerson.person.updateDate;
        } else if (qObject instanceof QJob){
            updateDatePath = QJob.job.updateDate;
        } else if (qObject instanceof QComment){
            updateDatePath = QComment.comment.updateDate;
        }

        return updateDatePath;
    }

    private NumberPath makeLikesPath(EntityPath qObject){

        NumberPath likesPath = null;

        if(qObject instanceof QPerson){
            likesPath = QPerson.person.likes;
        } else if (qObject instanceof QJob){
            likesPath = QJob.job.likes;
        } else if (qObject instanceof QComment){
            likesPath = QComment.comment.likes;
        }

        return likesPath;
    }

    private void checkIfKeywordNull() {
        if(filterBy == null || filterBy.equals("")){
            throw new IllegalArgumentException("filterBy 가 null 입니다.");
        }
        if( keyword == null || keyword.equals("")){
            throw new IllegalArgumentException("keyword 가 null 입니다.");
        }
    }
}
