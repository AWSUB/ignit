package com.ignit.internship.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.midtrans.Midtrans;

@Configuration
public class MidtransConfig {

    @Value("${midtrans.server-key}")
    private String serverKey;

    @Value("${midtrans.client-key}")
    private String clientKey;

    @Value("${midtrans.production}")
    private boolean isProduction;

    @Bean
    CommandLineRunner config() {
        return _ -> {
            Midtrans.serverKey = serverKey;
            Midtrans.clientKey = clientKey;
            Midtrans.isProduction = isProduction;
        };
    }
}
