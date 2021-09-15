package com.mbtizip.domain.candidate.person;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.candidate.CandidateDType;
import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.common.pageSortFilter.InterfaceForPageSortFilter;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Slf4j
@NoArgsConstructor
@Getter
@Entity
@DiscriminatorValue(CandidateDType.PERSON_D_TYPE)
public class Person extends Candidate implements InterfaceForPageSortFilter {

    @NotNull
    @Column(name = "person_name", length = 15, unique = true)
    private String name;

    @NotNull
    @Column(name = "person_gender")
    private Gender gender;

    @NotNull
    @Column(name = "person_description", length = 15)
    private String description;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Person(String name, String description, String writer,String password,  Gender gender){
        this.name = name;
        this.description = description;
        this.gender = gender;

        this.setWriterAndPassword(writer, password);
    }

    //== 연관관계 메서드 ==//

    public void setCategory(Category category) {
        this.category = category;
        category.getPersons().add(this);
    }
}

