package com.mbtizip.domain.comment.dto;

import com.mbtizip.domain.comment.Comment;
import lombok.Data;

@Data
public class CommentRegisterDto {

    private String writer;
    private String content;

    public Comment toEntity(){
        return Comment.builder()
                .writer(this.writer)
                .content(this.content)
                .build();
    }
}
