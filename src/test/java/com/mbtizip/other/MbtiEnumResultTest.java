package com.mbtizip.other;

import com.mbtizip.domain.mbti.Mbti;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.mbti.QMbti;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mbtizip.common.util.TestEntityGenerator.createPerson;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class MbtiEnumResultTest {

    @Autowired
    private JPAQueryFactory queryFactory;

    //@Test
    public void MBTI_ENUM_결과() {

        QMbti mbti = QMbti.mbti;

        List<Mbti> findMbti = queryFactory.selectFrom(mbti)
                .where(mbti.name.eq(MbtiEnum.ENTP))
                .fetch();

        assertEquals(findMbti.get(0).getName(), MbtiEnum.ENTP);
    }

}
