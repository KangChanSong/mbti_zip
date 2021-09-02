package com.mbtizip.common.util;

import com.mbtizip.common.enums.TestCategoryEnum;
import com.mbtizip.common.enums.TestCommentEnum;
import com.mbtizip.common.enums.TestFileEnum;
import com.mbtizip.common.enums.TestPersonEnum;
import com.mbtizip.domain.category.Category;
import com.mbtizip.domain.comment.Comment;
import com.mbtizip.domain.file.File;
import com.mbtizip.domain.file.FileId;
import com.mbtizip.domain.job.Job;
import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import com.mbtizip.domain.person.Gender;
import com.mbtizip.domain.person.Person;

import static com.mbtizip.common.enums.TestCommentEnum.COMMENT_CONTENT;
import static com.mbtizip.common.enums.TestCommentEnum.COMMENT_WRITER;
import static com.mbtizip.common.enums.TestFileEnum.FILE_NAME;
import static com.mbtizip.common.enums.TestFileEnum.FILE_UUID;
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
                .gender(Gender.MALE)
                .description(PERSON_DESCRIPTION.getText()).build();
    }

    public static Person createPerson(String name){
        return Person.builder()
                .name(name)
                .description(PERSON_DESCRIPTION.getText()).build();
    }


    public static Category createCategory(TestCategoryEnum name){
        return Category.builder()
                .name(name.getText())
                .build();
    }

    public static Category createCategory(String name){
        return Category.builder()
                .name(name)
                .build();
    }

    public static Comment createComment(){
        return Comment.builder()
                .writer(COMMENT_WRITER.getText())
                .content(COMMENT_CONTENT.getText())
                .build();
    }

    public static File createFile(){
        File file = new File();
        file.setFileId(FileId.builder()
                .uuid(FILE_UUID.getText())
                .name(FILE_NAME.getText())
                .build());
        return file;
    }
}
