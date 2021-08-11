package com.mbtizip.common.enums;

public enum TestCommentEnum {
    COMMENT_WRITER("댓글작성자"),
    COMMENT_CONTENT("댓글내용");

    private String text;

    TestCommentEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
