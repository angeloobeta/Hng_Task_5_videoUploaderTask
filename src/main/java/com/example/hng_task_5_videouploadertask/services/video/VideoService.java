package com.example.hng_task_5_videouploadertask.services.video;

import com.example.hng_task_5_videouploadertask.data.dto.payload.VideoRequestDto;
import com.example.hng_task_5_videouploadertask.data.dto.response.ApiResponseDto;
import com.example.hng_task_5_videouploadertask.data.dto.response.VideoResponseDto;
import com.example.hng_task_5_videouploadertask.data.entities.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VideoService {
   ApiResponseDto <List<VideoResponseDto>> uploadVideo(MultipartFile[] file) throws IOException;

    ApiResponseDto<Optional<Video>> findVideoById(String id);

    ApiResponseDto<List<VideoResponseDto>> getAllVideos();

    ApiResponseDto<List<VideoResponseDto>> findByFileName(String fileName);

    ApiResponseDto<String> deleteFileById(String fileId);
}