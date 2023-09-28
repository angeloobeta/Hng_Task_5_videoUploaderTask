package com.example.hng_task_5_videouploadertask.services.video;

import com.example.hng_task_5_videouploadertask.data.entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VideoService {
    Video uploadVideo(MultipartFile file) throws IOException;

    Optional<Video> getVideoById(Long id);

    List<Video> getAllVideos();

    List<Video> findByTimestampAfter(LocalDateTime timeStamp);


    List<Video> findByFileName(String fileName);
}