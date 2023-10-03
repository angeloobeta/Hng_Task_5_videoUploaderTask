package com.example.hng_task_5_videouploadertask.controller;

import com.example.hng_task_5_videouploadertask.data.dto.response.VideoResponseDto;
import com.example.hng_task_5_videouploadertask.data.repositories.VideoRepository;
import com.example.hng_task_5_videouploadertask.services.video.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@Tag(name = "Video Upload", description = "Chrome Extension Video Upload Endpoint")
@RequestMapping("/api/video")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;

    // upload video or media file for transcription

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    @Operation(summary = "Upload video/videos to the server",
            description = "Returns an ApiResponse Response entity containing successful message of the uploaded video/videos with details")
    public ResponseEntity<?> uploadVideo(
            @RequestPart("file")
            @RequestParam(value="fileName") MultipartFile[] fileName) {
        if (fileName[0].isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("You must select a file!");
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(videoService.uploadVideo(fileName));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // fetch all transcribed media files stored in the database
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = VideoResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "No has been uploaded; upload a video using the upload endpoint", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/all")
    @Operation(summary = "Get all videos stored in the in server",
            description = "Returns an ApiResponse Response entity containing a list of the videos in the server")

    public ResponseEntity<?> getAllVideos() {
        return ResponseEntity.ok(videoService.getAllVideos());
    }


    // fetch media file by id

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = VideoResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The video with given Id was not found.", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("fileId/{fileId}")
    @Operation(summary = "Get a video by its unique_id",
            description = "Returns an ApiResponse Response entity containing a the detail of a video")
    public ResponseEntity<?> findVideoById(@PathVariable String fileId) {
        return ResponseEntity.ok(videoService.findVideoById(fileId));
    }


    // fetch media file by fileName
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = VideoResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The video with given Name was not found, check the name and try again.", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("fileName/{fileName}")
    @Operation(summary = "Get a particular video by it's name",
            description = "Returns an ApiResponse Response entity containing a the detail of a video")
    public ResponseEntity<?> findByFileName(@RequestParam String fileName){
        return ResponseEntity.ok(videoService.findByFileName(fileName));
    }


    // delete media file by fileId
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = VideoResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The video with given timeStamp was not found, recheck the timeStamp and try again", content = { @Content(schema = @Schema()) })
    })@DeleteMapping("delete/{fileId}")
    @Operation(summary = "Delete a video by it's Id",
            description = "Returns an ApiResponse Response with successfully deletion of video")

    public ResponseEntity<?> deleteVideo(@PathVariable String fileId){
        return ResponseEntity.ok(videoService.deleteFileById(fileId));
    }
}
