package com.example.hng_task_5_videouploadertask.controller;

import com.example.hng_task_5_videouploadertask.data.dto.response.ApiResponseDto;
import com.example.hng_task_5_videouploadertask.data.dto.response.VideoResponseDto;
import com.example.hng_task_5_videouploadertask.services.video.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
public class VideoController {
    @Autowired
    private VideoService videoService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadVideo(@RequestParam("fileName") MultipartFile fileName) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(videoService.uploadVideo(fileName));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<?> getVideo(@PathVariable String fileId) {
        return ResponseEntity.ok(videoService.getVideoById(fileId));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllVideos() {
        return ResponseEntity.ok(videoService.getAllVideos());
    }

    @GetMapping("/fileName")
    public ResponseEntity<?> findByFileName(@PathVariable String fileName){
        return ResponseEntity.ok(videoService.findByFileName(fileName));
    }

    @GetMapping("/timeStamp")
    public ResponseEntity<?> findByTimeStamp(@PathVariable LocalDateTime timeStamp){
        return ResponseEntity.ok(videoService.findByTimestampAfter(timeStamp));
    }
}
