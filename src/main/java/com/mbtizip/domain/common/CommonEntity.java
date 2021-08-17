package com.mbtizip.domain.common;

import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.personCategory.PersonCategory;

public abstract class CommonEntity {

    public abstract void modifyLikes(Boolean isIncrease);
    public abstract void setPassword(String password);
    public void changeMbti(Mbti mbti){};
    public void increaseViews(){};
}
