package com.mbtizip.domain.comment;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.common.CommonEntity;
import com.mbtizip.domain.common.pageSortFilter.InterfaceForPageSortFilter;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.candidate.person.Person;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Slf4j
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Comment extends CommonEntity implements InterfaceForPageSortFilter {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @NotNull
    @Column(name = "comment_content")
    private String content;

    @NotNull
    @Column(name = "comment_writer")
    private String writer;

    @NotNull
    @Column(name = "comment_password")
    private String password;

    @Column(name = "comment_likes", columnDefinition = "integer default 0")
    private int likes;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mbti_id")
    private Mbti mbti;

    @Builder
    public Comment(String content, String writer, String password){
        this.content =content;
        this.writer = writer;
        this.password = password;
    }
    
    //==연관관계 메서드==//
    public void setCandidate(Candidate candidate){
        this.candidate = candidate;
        candidate.getComments().add(this);
    }

    public void setMbti(Mbti mbti){
        this.mbti = mbti;
        mbti.getComments().add(this);
    }

    public void update(Comment comment) {
        this.content = comment.getContent();
    }

    @Override
    public void modifyLikes(Boolean isIncrease) {
        if(isIncrease){
            this.likes++;
        } else {
            if(likes > 0) this.likes --;
        }
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
}

