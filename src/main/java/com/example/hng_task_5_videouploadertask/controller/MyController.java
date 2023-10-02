package com.example.hng_task_5_videouploadertask.controller;

import com.theokanning.openai.audio.CreateTranscriptionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class MyController {

    @Value("${openai.api.key}")
    private String apiKey;

    @GetMapping("/audio")
    public String audio(@RequestParam String filePath){
        OpenAiService service = new OpenAiService(apiKey);
        CreateTranscriptionRequest request = CreateTranscriptionRequest.builder()
                .model("whisper-1")
                .build();
        File file = new File(filePath);
        return service.createTranscription(request,filePath).getText();
    }
}
