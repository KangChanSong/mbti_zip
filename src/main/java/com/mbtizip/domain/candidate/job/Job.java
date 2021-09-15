package com.mbtizip.domain.candidate.job;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.candidate.CandidateDType;
import com.mbtizip.domain.common.pageSortFilter.InterfaceForPageSortFilter;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@DiscriminatorValue(CandidateDType.JOB_D_TYPE)
public class Job  extends Candidate implements InterfaceForPageSortFilter {

    @NotNull
    @Column(name = "job_title", length = 15, unique = true)
    private String title;

    @Builder
    public Job(String title, String writer, String password){
        this.title = title;
        this.setWriterAndPassword(writer, password);
    }
}
