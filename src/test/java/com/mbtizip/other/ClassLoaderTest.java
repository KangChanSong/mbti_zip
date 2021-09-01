package com.mbtizip.other;

import org.junit.jupiter.api.Test;

public class ClassLoaderTest {

    @Test
    public void 클래스로더(){
        ClassLoader loader = getClass().getClassLoader();
    }
}
