package com.mbtizip.domain.common;

import com.mbtizip.domain.mbti.Mbti;

public abstract class CommonEntity {

    public abstract void modifyLikes(Boolean isIncrease);

    public void changeMbti(Mbti mbti){};
}
