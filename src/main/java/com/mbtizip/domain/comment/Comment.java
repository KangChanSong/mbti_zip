package com.mbtizip.domain.comment;

import com.mbtizip.domain.common.CommonEntity;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.person.Person;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Comment extends CommonEntity{

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment_content")
    private String content;

    @Column(name = "comment_writer")
    private String writer;

    @Column(name = "comment_likes", columnDefinition = "integer default 0")
    private int likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mbti_id")
    private Mbti mbti;

    public void update(Comment comment) {
        this.content = comment.getContent();
        this.writer = comment.getWriter();
    }

    @Override
    public void modifyLikes(Boolean isIncrease) {
        if(isIncrease){
            this.likes++;
        } else {
            if(likes > 0) this.likes --;
        }
    }
}

