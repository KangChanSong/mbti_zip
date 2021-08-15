package com.mbtizip.domain.comment;

import com.mbtizip.domain.common.CommonEntity;
import com.mbtizip.domain.common.pageSortFilter.InterfaceForPageSortFilter;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.person.Person;
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

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment_content")
    private String content;

    @Column(name = "comment_writer")
    private String writer;

    @Column(name = "comment_password")
    private String password;

    @Column(name = "comment_likes", columnDefinition = "integer default 0")
    private int likes;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

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
    public void setJob(Job job){
        this.job = job;
        job.getComments().add(this);
    }

    public void setPerson(Person person){
        this.person = person;
        person.getComments().add(this);
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

