package com.example.monolith.controller;

import com.example.monolith.FeatureFlagService;
import com.example.monolith.RpsCounter;
import com.example.monolith.entity.Order;
import com.example.monolith.service.OrderService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final RpsCounter rpsCounter;
    private final FeatureFlagService featureFlagService;
    private final RestTemplate restTemplate;

    public OrderController(OrderService orderService,
                           RpsCounter rpsCounter,
                           FeatureFlagService featureFlagService,
                           RestTemplate restTemplate) {
        this.orderService = orderService;
        this.rpsCounter = rpsCounter;
        this.featureFlagService = featureFlagService;
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        rpsCounter.increment();

        if (featureFlagService.isUseMicroservices()) {
            // Новый путь: вызываем order-service
            return restTemplate.postForObject("http://localhost:8081/orders", order, Order.class);
        } else {
            // Старый путь: локальная логика монолита
            return orderService.createOrder(order);
        }
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        rpsCounter.increment();

        if (featureFlagService.isUseMicroservices()) {
            // Новый путь: получаем из order-service
            return restTemplate.getForObject("http://localhost:8081/orders/" + id, Order.class);
        } else {
            // Старый путь: локальная логика
            return orderService.getOrder(id);
        }
    }
}