package com.mbtizip.other;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SynchronizedMethod {
    private int sum = 0;

    public synchronized void calculate(){
        setSum(getSum()+1);
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}

public class SynchronizedTest {

    @Test
    public void givenMultiThread_whenNonSynchMethod() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        SynchronizedMethod summation = new SynchronizedMethod();

        IntStream.range(0, 1000)
                .forEach(count -> service.submit(() -> summation.calculate()));

        service.awaitTermination(1000, TimeUnit.MILLISECONDS);

        assertEquals(1000, summation.getSum());
    }
}
