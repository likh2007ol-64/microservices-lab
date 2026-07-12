package com.example.orderservice;

import com.example.orderservice.dto.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {
    private final RestTemplate restTemplate;
    private final String userServiceUrl = "http://localhost:8082/users/";

    public UserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto getUser(Long id) {
        return restTemplate.getForObject(userServiceUrl + id, UserDto.class);
    }
}