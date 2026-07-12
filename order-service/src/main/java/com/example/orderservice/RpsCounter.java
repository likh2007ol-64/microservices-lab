package com.example.orderservice;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class RpsCounter {
    private final AtomicLong counter = new AtomicLong(0);

    public void increment() {
        counter.incrementAndGet();
    }

    @Scheduled(fixedRate = 1000)
    public void logRps() {
        long count = counter.getAndSet(0);
        System.out.println("📊 OrderService RPS: " + count);
    }
}