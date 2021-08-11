package com.mbtizip.domain.common;

import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.personCategory.PersonCategory;

public abstract class CommonEntity {

    public abstract void modifyLikes(Boolean isIncrease);

    public void changeMbti(Mbti mbti){};
}
