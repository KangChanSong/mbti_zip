package com.mbtizip.other;

import com.mbtizip.domain.candidate.Candidate;
import com.mbtizip.domain.candidate.person.Person;
import org.junit.jupiter.api.Test;

import java.util.List;

interface TestInterface<T extends Candidate> {
    List<T> get(Class<T> cls);
}

class TestImpl implements TestInterface {

    @Override
    public List get(Class cls) {
        return null;
    }
}


public class GenericTest {

    @Test
    public void GenericIntefaceTest(){
        TestInterface test = new TestImpl();
        test.get(List.class);
    }


}
