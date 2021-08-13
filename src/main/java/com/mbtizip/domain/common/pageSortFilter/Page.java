package com.mbtizip.domain.common.pageSortFilter;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor
public class Page {
    private int pageNum;
    private int amount;
    private int offset;

    @Builder
    public Page(int pageNum, int amount){

        if(pageNum <= 0){
            pageNum = 1;
        }

        if(amount == 0){
            amount = 10;
        }
        this.pageNum = pageNum;
        this.amount =amount;
        this.offset = (pageNum-1) * amount;
    }
}
