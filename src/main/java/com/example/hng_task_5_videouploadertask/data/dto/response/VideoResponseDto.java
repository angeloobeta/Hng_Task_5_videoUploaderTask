package com.example.hng_task_5_videouploadertask.data.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class VideoResponseDto {
    private String fileName;
    private String fileId;
    private LocalDateTime timeStamp;
    private String fileSize;
    private String downloadUrl;

}
