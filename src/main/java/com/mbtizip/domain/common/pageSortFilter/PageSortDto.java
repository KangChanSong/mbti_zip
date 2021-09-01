package com.mbtizip.domain.common.pageSortFilter;

import com.mbtizip.domain.comment.QComment;
import com.mbtizip.domain.job.QJob;
import com.mbtizip.domain.person.QPerson;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.NumberPath;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageSortDto<T extends InterfaceForPageSortFilter> {
    private int page = 1;
    private int size = 10;
    private String sort = "createDate";
    private String dir = "desc";

    public void setDir(String dir) {
        if(dir == null | (!dir.equals("asc") && !dir.equals("desc"))){
            this.dir = "desc";
        } else {
            this.dir = dir;
        }
    }

    public Page toPage() {
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


    private OrderSpecifier createSort(EntityPath qObject){

        OrderSpecifier orderSpecifier = null;

        DateTimePath createDatePath = makeCreateDatePath(qObject);
        DateTimePath updateDatePath = makeUpdateDatePath(qObject);
        NumberPath likesPath = makeLikesPath(qObject);
        NumberPath viewsPath = makeViewsPath(qObject);

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
                case "views":
                    orderSpecifier = viewsPath.desc();
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
                case "views":
                    orderSpecifier = viewsPath.asc();
            }
        }

        return orderSpecifier;
    }

    private DateTimePath makeCreateDatePath(EntityPath qObject){

        return checkAndGetPath(qObject,
                QPerson.person.createDate,
                QJob.job.createDate,
                QComment.comment.createDate);
    }

    private DateTimePath makeUpdateDatePath(EntityPath qObject){

        return checkAndGetPath(qObject,
                QPerson.person.updateDate,
                QJob.job.updateDate,
                QComment.comment.updateDate);
    }

    private NumberPath makeLikesPath(EntityPath qObject){

        return checkAndGetPath(qObject,
                QPerson.person.likes,
                QJob.job.likes,
                QComment.comment.likes);
    }

    private NumberPath makeViewsPath(EntityPath qObject){
        return checkAndGetPath(qObject,
                QPerson.person.views,
                QJob.job.views, null);
    }

    private <T> T checkAndGetPath(EntityPath qObject, T personPath, T jobPath, T commentPath){
        T obj = null;
        if(qObject instanceof QPerson){
            obj = personPath;
        } else if (qObject instanceof QJob){
            obj = jobPath;
        } else if (qObject instanceof QComment){
            obj = commentPath;
        } else throw new IllegalArgumentException("인스턴스의 클래스가 적합하지 않습니다. class = " + obj.getClass().getSimpleName());

        return obj;
    }
}
