package com.mbtizip.domain.mbti;

public enum MbtiEnum {
    ISTJ("istj"), ISFJ("isfj"), INFJ("infj"), INTJ("intj"),
    ISTP("istp"), ISFP("isfp"), INFP("infp"), INTP("intp"),
    ESTP("estp"), ESFP("esfp"), ENFP("enfp"), ENTP("entp"),
    ESTJ("estj"), ESFJ("esfj"), ENFJ("enfj"), ENTJ("entj"),
    NONE("none"), DRAW("draw");

    private String text;
    MbtiEnum(String text){
        this.text = text;
    }
    public String getText() {
        return text;
    }
}
