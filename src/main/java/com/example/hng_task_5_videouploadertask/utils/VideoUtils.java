package com.example.hng_task_5_videouploadertask.utils;

import com.example.hng_task_5_videouploadertask.data.dto.response.VideoResponseDto;
import com.example.hng_task_5_videouploadertask.data.entity.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component @RequiredArgsConstructor
public class VideoUtils {
    public VideoResponseDto mapVideoToDto(Video video){
        return VideoResponseDto.builder()
                .timeStamp(video.getTimestamp())
                .fileUrl(video.getFileUrl())
                .fileId(String.valueOf(video.getId()))
                .fileName(video.getFilename())
                .fileSize(video.getFileSize())
                .build();
    }

//    public ResponseEntity<Resource> downloadVideo(){}

    public byte[] downloadVideo(String fileName) throws IOException {
//        byte [] videoFile = Files.readAllBytes(new File("lsnshhs/jshs").toPath());
        return Files.readAllBytes(new File("lsnshhs/jshs").toPath());
    }
}
