package com.mbtizip.domain.mbti;

import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"mbti_name"})})
public class Mbti {

    @Id @GeneratedValue
    @Column(name ="mbti_id")
    private Long id;

    @Column(name = "mbti_name", unique = true)
    private MbtiEnum name;

    @OneToMany(mappedBy = "mbti")
    private List<Person> persons = new ArrayList<>();

    @OneToMany(mappedBy = "mbti")
    private List<Job> jobs = new ArrayList<>();
    
    @OneToMany(mappedBy = "mbti")
    private List<Comment> comments = new ArrayList<>();
}
