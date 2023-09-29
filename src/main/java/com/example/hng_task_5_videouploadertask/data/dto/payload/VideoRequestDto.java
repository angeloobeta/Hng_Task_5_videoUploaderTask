package com.example.hng_task_5_videouploadertask.data.dto.payload;

import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter @NoArgsConstructor @AllArgsConstructor @Builder
public class VideoRequestDto {
    private String fileName;
    private LocalDateTime timeStamp;
    private String fileUrl;
    private Long fileSize;
}
