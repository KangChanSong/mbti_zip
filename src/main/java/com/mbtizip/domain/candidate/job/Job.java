package com.mbtizip.domain.candidate.job;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.candidate.DType;
import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.common.CommonEntity;
import com.mbtizip.domain.common.pageSortFilter.InterfaceForPageSortFilter;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.mbti.Mbti;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Entity
@DiscriminatorValue(DType.JOB_D_TYPE)
public class Job  extends Candidate implements InterfaceForPageSortFilter {

    @NotNull
    @Column(name = "job_title", length = 15)
    private String title;

    @Builder
    public Job(String title, String writer, String password){
        this.title = title;
        this.setWriterAndPassword(writer, password);
    }
}
