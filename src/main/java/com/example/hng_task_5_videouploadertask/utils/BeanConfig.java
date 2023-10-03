package com.example.hng_task_5_videouploadertask.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component @Service
public class BeanConfig {

    @Value("${OPENAI_API_KEY}")
    public String apiKey;
}
