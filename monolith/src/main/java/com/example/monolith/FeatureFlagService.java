package com.example.monolith;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FeatureFlagService {

    @Value("${feature.use-microservices:false}")
    private boolean useMicroservices;

    public boolean isUseMicroservices() {
        return useMicroservices;
    }
}