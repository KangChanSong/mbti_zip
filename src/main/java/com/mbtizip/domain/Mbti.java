package com.mbtizip.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Mbti {

    @Id @GeneratedValue
    @Column(name ="mbti_id")
    private Long id;

    @Column(name = "mbti_name")
    private String name;

}
