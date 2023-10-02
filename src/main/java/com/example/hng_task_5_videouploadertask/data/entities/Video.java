package com.example.hng_task_5_videouploadertask.data.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "videos")
public class Video {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String fileUrl;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    private String fileSize;



}

