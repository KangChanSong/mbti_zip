package com.mbtizip.domain.job;

import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.common.CommonEntity;
import com.mbtizip.domain.common.pageSortFilter.InterfaceForPageSortFilter;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.mbti.Mbti;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Entity
public class Job  extends CommonEntity implements InterfaceForPageSortFilter {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long id;

    @NotNull
    @Column(name = "job_title", length = 15)
    private String title;

    @NotNull
    @Column(name = "job_writer")
    private String writer;

    @NotNull
    @Column(name = "job_password")
    private String password;


    @Column(name = "job_likes", columnDefinition = "integer default 0")
    private int likes;

    @Column(name = "job_views" , columnDefinition = "integer default 0")
    private int views;

    @CreationTimestamp
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mbti_id")
    private Mbti mbti;

    @OneToMany(mappedBy = "job", cascade = ALL)
    private List<Comment> comments = new ArrayList<>();

    @NotNull
    @OneToOne(mappedBy = "job", fetch = LAZY, cascade = ALL)
    private File file;

    @Builder
    public Job(String title, String writer, String password){
        this.title = title;
        this.writer = writer;
        this.password =password;
    }
    //==연관관계 메서드==//
    @Override
    public void changeMbti(Mbti mbti) {
        this.mbti = mbti;
        if(mbti != null) mbti.getJobs().add(this);

    }

    public void setFile(File file) {
        this.file = file;
    }

    //== 편의 메서드 ==//
    @Override
    public void modifyLikes(Boolean isIncrease) {
        if(isIncrease){
            this.likes++;
        } else {
            if(likes > 0) this.likes --;
        }
    }

    @Override
    public void increaseViews() {
        this.views++;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }


}
