package com.mbtizip.domain.file;

import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.person.Person;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class File {

    @Id @GeneratedValue
    @Column(name = "file_id")
    private Long id;

    private String uuid;

    @Column(name = "file_name")
    private String name;

    @OneToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @Builder
    public File(String uuid, String name){
        this.uuid = uuid;
        this.name = name;
    }

    //== 연관관계 메서드 ==//
    public void setPerson(Person person) {
        this.person = person;
        person.setFile(this);
    }

    public void setJob(Job job){
        this.job = job;
        job.setFile(this);
    }

    //== 편의 메서드 ==//
    public void update(File file){
        this.uuid = file.getUuid();
        this.name = file.getName();
    }
}
