package com.microservices.address_service.commons.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfigurations {

    //@LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @LoadBalanced
//    @Bean
//    public WebClient webClient() {
//        return WebClient.builder().build();
//    }
}
