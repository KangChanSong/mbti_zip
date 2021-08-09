package com.mbtizip.domain.job;

import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.comment.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Job {

    @Id @GeneratedValue
    @Column(name = "job_id")
    private Long id;

    @Column(name = "job_title")
    private String title;

    @CreationTimestamp
    private LocalDateTime createDate;
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mbti_id")
    private Mbti mbti;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();
}
