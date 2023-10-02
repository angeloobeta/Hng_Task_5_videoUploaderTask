package com.example.hng_task_5_videouploadertask.controller;

import com.example.hng_task_5_videouploadertask.data.dto.response.VideoResponseDto;
import com.example.hng_task_5_videouploadertask.data.repositories.VideoRepository;
import com.example.hng_task_5_videouploadertask.services.video.VideoService;
import com.theokanning.openai.audio.CreateTranscriptionRequest;
import com.theokanning.openai.service.OpenAiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@Tag(name = "Video Upload", description = "Chrome Extension Video Upload Endpoint")
@RequestMapping("/api/video")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final VideoRepository videoRepository;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    @Operation(summary = "Upload video/videos to the server",
            description = "Returns an ApiResponse Response entity containing successful message of the uploaded video/videos with details")
    public ResponseEntity<?> uploadVideo(
            @RequestPart("file")
            @RequestParam(value = "fileName")
            MultipartFile[] fileName) {
        if (fileName[0].isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("You must select a file!");
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(videoService.uploadVideo(fileName));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = VideoResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The video with given Id was not found.", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/{fileId}")
    @Operation(summary = "Get a video by its unique_id",
            description = "Returns an ApiResponse Response entity containing a the detail of a video")
    public ResponseEntity<?> getVideo(@RequestParam String fileId) {
        return ResponseEntity.ok(videoService.getVideoById(fileId));
    }

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

    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = VideoResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The video with given Name was not found, check the name and try again.", content = { @Content(schema = @Schema()) })
    })
    @GetMapping("/{fileName}")
    @Operation(summary = "Get a particular video by it's name",
            description = "Returns an ApiResponse Response entity containing a the detail of a video")
    public ResponseEntity<?> findByFileName(@RequestParam String fileName){
        return ResponseEntity.ok(videoService.findByFileName(fileName));
    }


    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = VideoResponseDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", description = "The video with given timeStamp was not found, recheck the timeStamp and try again", content = { @Content(schema = @Schema()) })
    })@GetMapping("/{timeStamp}")
    @Operation(summary = "Get a particular video by it's time_stamp",
            description = "Returns an ApiResponse Response entity containing a the detail of a video")

    public ResponseEntity<?> findByTimeStamp(@RequestParam LocalDateTime timeStamp){
        return ResponseEntity.ok(videoService.findByTimestampAfter(timeStamp));
    }

    @Value("${openai.api.key}")
    private String apiKey;

    @GetMapping("/audio")
    public String audio(@RequestParam String filePath){
        OpenAiService service = new OpenAiService(apiKey);

        CreateTranscriptionRequest request = new CreateTranscriptionRequest();
        request.setModel("whisper-1");
        File file = new File(filePath);
        return service.createTranscription(request,filePath).getText();
    }
}



//package controller;
//
//        import java.io.File;
//        import java.io.IOException;
//        import java.nio.file.Files;
//        import java.nio.file.Path;
//        import java.nio.file.Paths;
//
//        import org.springframework.stereotype.Controller;
//        import org.springframework.ui.Model;
//        import org.springframework.web.bind.annotation.RequestMapping;
//        import org.springframework.web.bind.annotation.RequestParam;
//        import org.springframework.web.multipart.MultipartFile;
//
//@Controller
//public class FileUploadController {
//    public static String uploadDirectory = System.getProperty("user.dir")+"/uploads";
//    @RequestMapping("/")
//    public String UploadPage(Model model) {
//        return "uploadview";
//    }
//    @RequestMapping("/upload")
//    public String upload(Model model,@RequestParam("files") MultipartFile[] files) {
//        StringBuilder fileNames = new StringBuilder();
//        for (MultipartFile file : files) {
//            Path fileNameAndPath = Paths.get(uploadDirectory, file.getOriginalFilename());
//            fileNames.append(file.getOriginalFilename()+" ");
//            try {
//                Files.write(fileNameAndPath, file.getBytes());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        model.addAttribute("msg", "Successfully uploaded files "+fileNames.toString());
//        return "uploadstatusview";
//    }
//
//
//}
