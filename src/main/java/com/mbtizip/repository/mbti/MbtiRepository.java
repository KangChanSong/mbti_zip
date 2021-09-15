package com.mbtizip.repository.mbti;

import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;

import java.util.List;

public interface MbtiRepository {
    Long save(Mbti job);
    Mbti find(Long id);
    Mbti findByName(MbtiEnum name);
    List<Mbti> findAll();
}
