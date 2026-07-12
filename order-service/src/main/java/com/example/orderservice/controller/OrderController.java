package com.example.orderservice.controller;

import com.example.orderservice.RpsCounter;
import com.example.orderservice.entity.Order;
import com.example.orderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final RpsCounter rpsCounter;

    public OrderController(OrderService orderService, RpsCounter rpsCounter) {
        this.orderService = orderService;
        this.rpsCounter = rpsCounter;
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        rpsCounter.increment();
        return orderService.createOrder(order);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        rpsCounter.increment();
        return orderService.getOrder(id);
    }
}