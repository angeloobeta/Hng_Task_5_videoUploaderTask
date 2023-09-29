package com.example.hng_task_5_videouploadertask.utils;

import com.example.hng_task_5_videouploadertask.data.dto.response.VideoResponseDto;
import com.example.hng_task_5_videouploadertask.data.entity.Video;
import com.example.hng_task_5_videouploadertask.data.repositories.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component @RequiredArgsConstructor
public class VideoUtils {

    public VideoResponseDto mapVideoToDto(Video video){
        return VideoResponseDto.builder()
                .timeStamp(video.getTimestamp())
                .downloadUrl(video.getFileUrl())
                .fileId(video.getId())
                .fileName(video.getFilename())
                .fileSize(video.getFileSize())
                .build();
    }

//    public ResponseEntity<Resource> downloadVideo(){}
}
