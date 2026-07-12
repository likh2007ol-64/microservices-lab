package com.example.monolith.loadtest;

import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class LoadTestRunner {

    private static final String ORDER_URL = "http://localhost:8080/orders";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final AtomicLong requestCounter = new AtomicLong(0);

    public static void main(String[] args) throws InterruptedException {
        int rps = 10; // запросов в секунду
        int durationSeconds = 30; // длительность теста

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(rps);

        // Запускаем задачи с фиксированной задержкой (1000 мс / rps)
        long delayBetweenRequests = 1000 / rps; // 100 мс

        for (int i = 0; i < durationSeconds * rps; i++) {
            scheduler.schedule(() -> sendOrderRequest(), i * delayBetweenRequests, TimeUnit.MILLISECONDS);
        }

        // Ждём завершения всех задач
        scheduler.shutdown();
        scheduler.awaitTermination(durationSeconds + 2, TimeUnit.SECONDS);

        System.out.println("Тест завершён. Всего отправлено запросов: " + requestCounter.get());
    }

    private static void sendOrderRequest() {
        try {
            // Создаём заказ с userId=1 (пользователь должен быть создан в user-service)
            String orderJson = "{\"pizza\":\"Маргарита\",\"userId\":1,\"status\":\"PENDING\"}";
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
            org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(orderJson, headers);

            restTemplate.postForObject(ORDER_URL, entity, String.class);
            requestCounter.incrementAndGet();
        } catch (Exception e) {
            System.err.println("Ошибка при отправке запроса: " + e.getMessage());
        }
    }
}