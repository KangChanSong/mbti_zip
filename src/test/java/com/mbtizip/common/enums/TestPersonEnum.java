package com.mbtizip.common.enums;

public enum TestPersonEnum {
    PERSON_NAME("송강찬"), PERSON_DESCRIPTION("INFP 로 추정한다.");
    private String text;

    TestPersonEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
