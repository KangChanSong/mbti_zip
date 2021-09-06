package com.mbtizip.other;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ComparatorTest {

    @Test
    public void testComparator(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        list.sort((o1 , o2) -> -1);

        list.forEach(System.out::println);
    }
}
