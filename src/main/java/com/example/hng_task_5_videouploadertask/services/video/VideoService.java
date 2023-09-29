package com.example.hng_task_5_videouploadertask.services.video;

import com.example.hng_task_5_videouploadertask.data.dto.response.ApiResponseDto;
import com.example.hng_task_5_videouploadertask.data.dto.response.VideoResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VideoService {
   ApiResponseDto <VideoResponseDto> uploadVideo(MultipartFile file) throws IOException;

    ApiResponseDto<VideoResponseDto> getVideoById(String id);

    ApiResponseDto<List<VideoResponseDto>> getAllVideos();

    ApiResponseDto<List<VideoResponseDto>> findByTimestampAfter(LocalDateTime timeStamp);


    ApiResponseDto<List<VideoResponseDto>> findByFileName(String fileName);
}