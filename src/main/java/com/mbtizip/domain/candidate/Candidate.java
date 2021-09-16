package com.mbtizip.domain.candidate;

import com.mbtizip.domain.BaseEntity;
import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.mbti.Mbti;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Getter
@NoArgsConstructor
@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
public abstract class Candidate extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "candidate_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mbti_id")
    private Mbti mbti;

    private String writer;

    private String password;

    @Column(columnDefinition = "integer default 0")
    private int likes;

    @Column(columnDefinition = "integer default 0")
    private int views;

    @OneToMany(mappedBy = "candidate", cascade = ALL)
    private List<Comment> comments = new ArrayList<>();

    @NotNull
    @OneToOne(mappedBy = "candidate", fetch = LAZY, cascade = ALL)
    private File file;

    //== 연관관계 메서드 ==//
    public void changeMbti(Mbti mbti) {
        this.mbti = mbti;
    }

    //== 편의 메서드 ==//
    public void setWriterAndPassword(String writer, String password){
        this.writer = writer;
        this.password = password;
    }

    public void modifyLikes(Boolean isIncrease) {
        if(isIncrease){
            this.likes++;
        } else {
            this.likes--;
        }
    }
    public void increaseViews() {
        this.views++;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
