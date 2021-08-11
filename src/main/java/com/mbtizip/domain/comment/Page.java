package com.mbtizip.domain.comment;

import lombok.*;

@Getter
@NoArgsConstructor
public class Page {
    private int start;
    private int end;

    @Builder
    public Page(int start, int end){
        this.start = start;
        this.end =end;
    }
}
