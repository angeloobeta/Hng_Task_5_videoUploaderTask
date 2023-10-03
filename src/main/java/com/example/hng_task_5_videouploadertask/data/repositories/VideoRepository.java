package com.example.hng_task_5_videouploadertask.data.repositories;

import com.example.hng_task_5_videouploadertask.data.entities.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {

    // Custom query to find videos by filename
    List<Video> findByFilename(String fileName);
    // Custom query to find videos uploaded after a certain timestamp
    Optional<Video> findVideoById(String id);

}

