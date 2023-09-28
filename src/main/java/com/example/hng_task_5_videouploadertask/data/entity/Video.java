package com.example.hng_task_5_videouploadertask.data.entity;

import com.example.hng_task_5_videouploadertask.services.video.VideoService;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private LocalDateTime timestamp;

}

