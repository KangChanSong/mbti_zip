package com.mbtizip.other;

import com.mbtizip.domain.person.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenericMethodTest {

    @Test
    public void 지네릭_메서드(){
        List<Integer> integers = Arrays.asList(1,2,3,4,5);
        Integer integer = createObject(integers);
        assertEquals(integer.intValue(), 1);

        List<String> strings = Arrays.asList("a", "b", "c", "d", "e");
        String string = createObject(strings);
        assertEquals(string.toString(), "a");
    }

    private <T> T createObject(List<T> list){
        return list.get(0);
    }
}
