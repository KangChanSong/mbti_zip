package com.mbtizip.domain.personCategory;

import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.person.Person;
import lombok.*;

import javax.persistence.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class PersonCategory {

    @Id @GeneratedValue
    @Column(name = "person_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;
}
