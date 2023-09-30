package com.example.hng_task_5_videouploadertask.data.repositories;

import com.example.hng_task_5_videouploadertask.data.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface VideoRepository extends JpaRepository<Video, String> {

    // Custom query to find videos by filename
    List<Video> findByFilename(String fileName);
    // Custom query to find videos uploaded after a certain timestamp
    List<Video> findByTimestampAfter(LocalDateTime timeStamp);

}

