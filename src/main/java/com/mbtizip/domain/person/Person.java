package com.mbtizip.domain.person;

import com.mbtizip.domain.common.CommonEntity;
import com.mbtizip.domain.common.pageSortFilter.InterfaceForPageSortFilter;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.personCategory.PersonCategory;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Person extends CommonEntity implements InterfaceForPageSortFilter {

    @Id @GeneratedValue
    @Column(name ="person_id")
    private Long id;

    @Column(name = "person_name" )
    private String name;

    @Column(name = "person_gender")
    private Gender gender;

    @Column(name = "person_description")
    private String description;

    @Column(name = "person_writer")
    private String writer;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mbti_id")
    private Mbti mbti;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<PersonCategory> personCategories = new ArrayList<>();

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
        this.views++;
    }
}

