package com.example.hng_task_5_videouploadertask.controller;

import com.example.hng_task_5_videouploadertask.data.entity.Video;
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
    public ResponseEntity<Video> uploadVideo(@RequestParam("file") MultipartFile file) {
        try {
            Video uploadedVideo = videoService.uploadVideo(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(uploadedVideo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Video> getVideo(@PathVariable Long id) {
        Optional<Video> video = videoService.getVideoById(id);
        return video.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Video>> getAllVideos() {
        List<Video> videos = videoService.getAllVideos();
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/fileName")
    public ResponseEntity<List<Video>> findByFileName(@PathVariable String fileName){
        List<Video> videos = videoService.findByFileName(fileName);
        return ResponseEntity.ok(videos);
    }

    @GetMapping("/timeStamp")
    public ResponseEntity<List<Video>> findByTimeStamp(@PathVariable LocalDateTime timeStamp){
        List<Video> videos = videoService.findByTimestampAfter(timeStamp);
        return ResponseEntity.ok(videos);
    }
}
