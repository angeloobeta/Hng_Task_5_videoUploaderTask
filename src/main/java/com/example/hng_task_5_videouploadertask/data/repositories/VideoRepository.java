package com.example.hng_task_5_videouploadertask.data.repositories;

import com.example.hng_task_5_videouploadertask.data.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {

    // Custom query to find videos by filename
    List<Video> findByFilename(String fileName);
    // Custom query to find videos uploaded after a certain timestamp
    List<Video> findByTimestampAfter(LocalDateTime timeStamp);

    Optional<Video> findVideoById(Long id);

}

