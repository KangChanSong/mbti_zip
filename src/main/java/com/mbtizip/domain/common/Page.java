package com.mbtizip.domain.common;

import lombok.*;

@Getter
@NoArgsConstructor
public class Page {
    private int start;
    private int end;

    @Builder
    public Page(int start, int end){
        if(start == 0){
            start = 1;
        }
        if(end == 0){
            end = 10;
        }
        this.start = start;
        this.end =end;
    }
}
