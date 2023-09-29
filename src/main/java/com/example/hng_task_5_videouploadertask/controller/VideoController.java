package com.example.hng_task_5_videouploadertask.controller;

import com.example.hng_task_5_videouploadertask.data.dto.payload.VideoRequestDto;
import com.example.hng_task_5_videouploadertask.data.dto.response.ApiResponseDto;
import com.example.hng_task_5_videouploadertask.data.dto.response.VideoResponseDto;
import com.example.hng_task_5_videouploadertask.data.entity.Video;
import com.example.hng_task_5_videouploadertask.data.repositories.VideoRepository;
import com.example.hng_task_5_videouploadertask.services.video.VideoService;
import lombok.RequiredArgsConstructor;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final VideoRepository videoRepository;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadVideo(@RequestParam("fileName") MultipartFile[] fileName) {
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

    @GetMapping("/downloadVideo/{fileId}")
    public ResponseEntity<?> downloadVideoFile(@PathVariable String fileId){
        Optional<Video> videoResponseDto = videoRepository.findById(fileId);

        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(videoResponseDto.get().getFilename()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \"" + videoResponseDto.get().getFilename());
//                .body(new ByteArrayResource(videoResponseDto.get().getData()));
    }
}
