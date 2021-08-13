package com.mbtizip.other;

import com.mbtizip.common.util.TestEntityGenerator;
import com.mbtizip.domain.mbti.MbtiEnum;
import com.mbtizip.domain.mbtiCount.MbtiCount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMaxTest {

    @Test
    public void Stream으로_최댓값_구하기(){

        //given
        List<MbtiCount> mbtiCounts = new ArrayList<>();

        for(int i = 0 ; i <= 10 ; i++){
            MbtiCount mbtiCount = TestEntityGenerator.createMbtiCount(MbtiEnum.INFP);
            for(int j = 0 ; j < i ; j++){
                mbtiCount.updateCount(true);
            }
            mbtiCounts.add(mbtiCount);
        }

        //when

        MbtiCount max = mbtiCounts.stream().sorted(new Comparator<MbtiCount>() {
            @Override
            public int compare(MbtiCount o1, MbtiCount o2) {
                return o1.getCount() < o2.getCount() ? 1 : -1;
            }
        }).collect(Collectors.toList()).get(0);

        //then
        Assertions.assertEquals(max.getCount(), 10);
    }
}
