package com.example.hng_task_5_videouploadertask.controller;

import com.example.hng_task_5_videouploadertask.data.dto.payload.VideoRequestDto;
import com.example.hng_task_5_videouploadertask.data.dto.response.ApiResponseDto;
import com.example.hng_task_5_videouploadertask.data.dto.response.VideoResponseDto;
import com.example.hng_task_5_videouploadertask.data.entity.Video;
import com.example.hng_task_5_videouploadertask.data.repositories.VideoRepository;
import com.example.hng_task_5_videouploadertask.services.video.VideoService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final VideoRepository videoRepository;

    @Operation(summary = "Upload video/videos to the server",
            description = "Returns an ApiResponse Response entity containing successful message of the uploaded video/videos with details")
    @PostMapping("/upload")
    public ResponseEntity<?> uploadVideo(@RequestParam("fileName") MultipartFile[] fileName) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(videoService.uploadVideo(fileName));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get a video by its unique_id",
            description = "Returns an ApiResponse Response entity containing a the detail of a video")
    @GetMapping("/{fileId}")
    public ResponseEntity<?> getVideo(@RequestBody String fileId) {
        return ResponseEntity.ok(videoService.getVideoById(fileId));
    }

    @Operation(summary = "Get all videos stored in the in server",
            description = "Returns an ApiResponse Response entity containing a list of the videos in the server")
    @GetMapping("/all")
    public ResponseEntity<?> getAllVideos() {
        return ResponseEntity.ok(videoService.getAllVideos());
    }

    @Operation(summary = "Get a particular video by it's name",
            description = "Returns an ApiResponse Response entity containing a the detail of a video")
    @GetMapping("/{fileName}")
    public ResponseEntity<?> findByFileName(@RequestBody String fileName){
        return ResponseEntity.ok(videoService.findByFileName(fileName));
    }

    @Operation(summary = "Get a particular video by it's time_stamp",
            description = "Returns an ApiResponse Response entity containing a the detail of a video")
    @GetMapping("/{timeStamp}")
    public ResponseEntity<?> findByTimeStamp(@RequestBody LocalDateTime timeStamp){
        return ResponseEntity.ok(videoService.findByTimestampAfter(timeStamp));
    }

//    @GetMapping("/downloadVideo/{fileId}")
//    public ResponseEntity<?> downloadVideoFile(@RequestBody String fileId){
//        Optional<Video> videoResponseDto = videoRepository.findById(fileId);
//
//        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.parseMediaType(videoResponseDto.get().getFilename()))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = \"" + videoResponseDto.get().getFilename());
////                .body(new ByteArrayResource(videoResponseDto.get().getData()));
//    }
}
