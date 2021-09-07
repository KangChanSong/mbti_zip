package com.mbtizip.domain.category;

import com.mbtizip.domain.person.Person;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"category_name"})})
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Person> persons = new ArrayList<>();

    @Builder
    public Category(String name){
        this.name = name;
    }

}
