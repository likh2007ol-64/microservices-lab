package com.example.monolith.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pizza;
    private Long userId;
    private String status;

    public Order() {}

    public Order(Long id, String pizza, Long userId, String status) {
        this.id = id;
        this.pizza = pizza;
        this.userId = userId;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPizza() { return pizza; }
    public void setPizza(String pizza) { this.pizza = pizza; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}