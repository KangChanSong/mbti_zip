package com.mbtizip.domain.comment;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment_content")
    private String content;

    @Column(name = "comment_writer")
    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;
}

