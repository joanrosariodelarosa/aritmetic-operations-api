package com.aritmetic.op.api.services;

import com.aritmetic.op.api.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RandomStringService {
    public String getRandomString() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(Constants.RANDOM_STRING_API, String.class);
    }
}
