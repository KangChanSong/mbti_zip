package com.mbtizip.controller.like;

import com.mbtizip.domain.common.wrapper.BooleanResponseDto;
import com.mbtizip.service.comment.CommentService;
import com.mbtizip.service.job.JobService;
import com.mbtizip.service.person.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/like")
public class LikeController {

    public static final String TARGET_PERSON = "person";
    public static final String TARGET_JOB = "job";
    public static final String TARGET_COMMENT = "comment";

    private final PersonService personService;
    private final JobService jobService;
    private final CommentService commentService;

    @PostMapping("/api/v1/{target}/{targetId}")
    public BooleanResponseDto addLike(){
        return null;
    }
}
