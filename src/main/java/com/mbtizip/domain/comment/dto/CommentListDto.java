package com.mbtizip.domain.comment.dto;

import com.mbtizip.domain.comment.Comment;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class CommentListDto {

    private List<CommentGetDto> commentGetDtos;

    public static CommentListDto toDto(List<Comment> comments){
        if(comments == null) return null;
        return CommentListDto.builder()
                .commentGetDtos(
                        comments.stream().map(comment -> CommentGetDto.toDto(comment))
                                .collect(Collectors.toList())
                ).build();
    }
}
