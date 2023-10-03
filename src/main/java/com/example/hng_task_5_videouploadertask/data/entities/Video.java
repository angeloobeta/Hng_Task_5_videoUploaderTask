package com.example.hng_task_5_videouploadertask.data.entities;

import com.theokanning.openai.audio.CreateTranscriptionRequest;
import com.theokanning.openai.service.OpenAiService;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Entity @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "videos")
public class Video {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String fileUrl;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private String fileSize;
    @Column(columnDefinition = "TEXT")
    private String transcriptionText;
}

