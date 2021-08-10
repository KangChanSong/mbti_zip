package com.mbtizip.common.util;

import com.mbtizip.common.enums.TestPersonEnum;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.person.Person;

import static com.mbtizip.common.enums.TestJobEnum.JOB_TITLE;
import static com.mbtizip.common.enums.TestJobEnum.JOB_WRITER;
import static com.mbtizip.common.enums.TestPersonEnum.PERSON_DESCRIPTION;
import static com.mbtizip.common.enums.TestPersonEnum.PERSON_NAME;

public class TestEntityGenerator {

    public static Mbti createMbti(MbtiEnum name) {

        return Mbti.builder()
                .name(name)
                .build();
    }

    public static Job createJob() {
        return Job.builder()
                .title(JOB_TITLE.getText())
                .writer(JOB_WRITER.getText())
                .build();
    }

    public static MbtiCount createMbtiCount(MbtiEnum name){
        return MbtiCount.builder()
                .mbti(createMbti(name))
                .build();
    }

    public static Person createPerson(){
        return Person.builder()
                .name(PERSON_NAME.getText())
                .description(PERSON_DESCRIPTION.getText()).build();
    }
}
