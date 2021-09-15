package com.mbtizip.domain.category;

import com.mbtizip.domain.candidate.person.Person;
import com.sun.istack.NotNull;
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

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @NotNull
    @Column(name = "category_name")
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Person> persons = new ArrayList<>();

    @Builder
    public Category(String name){
        this.name = name;
    }

}
