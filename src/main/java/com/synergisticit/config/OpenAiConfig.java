package com.synergisticit.config;

import com.theokanning.openai.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author GesangZeren
 * @project HotelReservation
 * @date 2/01/2025
 */
@Configuration
public class OpenAiConfig {

    @Bean
    public OpenAiService openAiService(@Value("${spring.ai.openai.api-key}") String apiKey) {
        return new OpenAiService(apiKey);
    }
}
