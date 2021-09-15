package com.mbtizip.domain.mbti;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.candidate.job.Job;
import com.mbtizip.domain.candidate.person.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"mbti_name"})})
public class Mbti {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="mbti_id")
    private Long id;

    @Column(name = "mbti_name", unique = true)
    private MbtiEnum name;

    @OneToMany(mappedBy = "mbti")
    private List<Candidate> candidates = new ArrayList<>();

    @OneToMany(mappedBy = "mbti")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    public Mbti(MbtiEnum name){
        this.name = name;
    }
}
