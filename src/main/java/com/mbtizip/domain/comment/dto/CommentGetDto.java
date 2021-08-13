package com.mbtizip.domain.comment.dto;

import com.mbtizip.domain.comment.Comment;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommentGetDto {

    private Long id;
    private String writer;
    private String content;

    public static CommentGetDto toDto(Comment comment){
        return CommentGetDto.builder()
                .id(comment.getId())
                .writer(comment.getWriter())
                .content(comment.getContent())
                .build();
    }
}
