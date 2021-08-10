package com.mbtizip.other;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.AbstractList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MockitoTest {

    @Test
    @DisplayName("Mockito.when().then() 메서드 테스트")
    public void WHEN_TEST() {

        MyList mockList = Mockito.mock(MyList.class);
        when(mockList.add(anyString())).thenReturn(false);

        boolean added = mockList.add("randomString");
        assertEquals(added, false);
    }

    @Test
    @DisplayName("Mockito 반환값 지정하는 다른방법 테스트")
    public void ANOTHER_WAY(){

        MyList mockList = Mockito.mock(MyList.class);
        doReturn(false).when(mockList).add(anyString());

        boolean added = mockList.add("randomString");
        assertEquals(added, false);
    }
    
    @Test
    @DisplayName("Mock 오브젝트가 예외를 던지게 하는 테스트")
    public void EXCEPTION(){
        MyList mockList = Mockito.mock(MyList.class);

        when(mockList.add(anyString())).thenThrow(IllegalStateException.class);

        assertThrows(IllegalStateException.class, ()->{
           mockList.add("randomString");
        });
    }

    @Test
    @DisplayName("아무것도 반환하지 않는 메소드에 대한 설정 테스트")
    public void VOID_METHOD(){
        MyList mockList = Mockito.mock(MyList.class);
        doThrow(NullPointerException.class).when(mockList).clear();

        assertThrows(NullPointerException.class, () -> {
           mockList.clear();
        });
    }

    @Test
    @DisplayName("여러번 메서드를 실행했을때의 설정 테스트")
    public void MULTIPLE_CALLS(){
        MyList mockList = Mockito.mock(MyList.class);
        when(mockList.add(anyString()))
                .thenReturn(false)
                .thenThrow(IllegalStateException.class);

        boolean firstCall =  mockList.add("randomString");
        assertEquals(firstCall, false);

        assertThrows(IllegalStateException.class , () -> mockList.add("randomString"));
    }

    @Test
    @DisplayName("Spy 오브젝트의 동작 조작 테스트")
    public void SPY(){
        MyList instance = new MyList();
        MyList spy = Mockito.spy(instance);

        doThrow(NullPointerException.class).when(spy).size();

        assertThrows(NullPointerException.class, ()-> spy.size());
    }

    @Test
    @DisplayName("Spy 오브젝트의 실제 메서드를 호출하게 하는 테스트")
    public void SPY_REAL_METHOD(){
        MyList instance = new MyList();
        MyList spy = Mockito.spy(instance);

        when(spy.size()).thenCallRealMethod();

        assertEquals(spy.size(), 1);
    }

    @Test
    @DisplayName("Mock 오브젝트의 메서드가 지정한 값을 반환하게 하는 테스트")
    public void CUSTOM_ANSWER(){
        MyList mockList = Mockito.mock(MyList.class);
        doAnswer(invocation -> "Always the same").when(mockList).get(anyInt());

        String element = mockList.get(1);
        assertEquals(element, "Always the same");
    }
}

class MyList extends AbstractList<String>{

    @Override
    public String get(int index) {
        return null;
    }

    @Override
    public int size() {
        return 1;
    }
}
