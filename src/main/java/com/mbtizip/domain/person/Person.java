package com.mbtizip.domain.person;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.CommonEntity;
import com.mbtizip.domain.common.pageSortFilter.InterfaceForPageSortFilter;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.comment.Comment;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Slf4j
@NoArgsConstructor
@Getter
@Entity
public class Person extends CommonEntity implements InterfaceForPageSortFilter {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="person_id")
    private Long id;

    @NotNull
    @Column(name = "person_name", length = 15)
    private String name;

    @NotNull
    @Column(name = "person_gender")
    private Gender gender;

    @NotNull
    @Column(name = "person_description", length = 15)
    private String description;

    @NotNull
    @Column(name = "person_writer")
    private String writer;

    @NotNull
    @Column(name = "person_password")
    private String password;

    @Column(name = "person_likes", columnDefinition = "integer default 0")
    private int likes;

    @Column(name = "person_views" , columnDefinition = "integer default 0")
    private int views;

    @CreationTimestamp
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mbti_id")
    private Mbti mbti;

    @OneToMany(mappedBy = "person", cascade = ALL)
    private List<Comment> comments = new ArrayList<>();

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @OneToOne(mappedBy = "person", fetch = LAZY, cascade = ALL)
    private File file;

    @Builder
    public Person(String name, String description, String writer,String password,  Gender gender){
        this.name = name;
        this.description = description;
        this.writer = writer;
        this.password = password;
        this.gender = gender;
    }

    //== 연관관계 메서드 ==//
    @Override
    public void changeMbti(Mbti mbti) {
        this.mbti = mbti;
        if(mbti != null) mbti.getPersons().add(this);
    }

    public void setCategory(Category category) {
        this.category = category;
        category.getPersons().add(this);
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
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void increaseViews() {
        log.info("조회수 올리는 메서드");
        this.views++;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

