package com.mbtizip.domain.file;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter @Setter
@Entity
public class File {
    @EmbeddedId
    private FileId fileId;
    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;
    @OneToOne
    @JoinColumn(name = "job_id")
    private Job job;

    public File(FileId fileId){
        this.fileId = fileId;
    }
}
