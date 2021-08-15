package com.mbtizip.domain.comment.dto;

import com.mbtizip.domain.comment.Comment;
import lombok.Data;

@Data
public class CommentUpdateDto {

    private String content;

    public Comment toEntity(){
        return Comment.builder()
                .content(this.content)
                .build();
    }
}
