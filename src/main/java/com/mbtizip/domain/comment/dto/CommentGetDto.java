package com.mbtizip.domain.comment.dto;

import com.mbtizip.domain.comment.Comment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CommentGetDto {

    private Long id;
    private String writer;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Integer likes;

    public static CommentGetDto toDto(Comment comment){
        return CommentGetDto.builder()
                .id(comment.getId())
                .writer(comment.getWriter())
                .content(comment.getContent())
                .createDate(comment.getCreateDate())
                .updateDate(comment.getUpdateDate())
                .likes(comment.getLikes())
                .build();
    }
}
