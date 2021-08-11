package com.mbtizip.common.enums;

public enum TestCategoryEnum {
    CATEGORY_NAME_1("철학가"),
    CATEGORY_NAME_2("음악인"),
    CATEGORY_NAME_3("연기자");

    private String text;

    TestCategoryEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
