package com.example.hng_task_5_videouploadertask.services.video;

import com.example.hng_task_5_videouploadertask.data.entity.Video;
import com.example.hng_task_5_videouploadertask.repositories.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VideoServiceImpl implements  VideoService{

    @Autowired
    private VideoRepository videoRepository;

//    @Value("${video.upload.path}") // Configure the path where videos will be saved on the file system
    private String uploadPath;

    @Transactional
    public Video uploadVideo(MultipartFile file) throws IOException {
        // Check if the upload directory exists; create it if not
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Generate a unique filename for the video
        String filename = LocalDateTime.now() + "_" + file.getOriginalFilename();

        // Create a new Video entity
        Video video = Video.builder()
                .filename(filename)
                .timestamp(LocalDateTime.now())
                .build();

        // Save the video file to the specified upload directory on the file system
        String filePath = uploadPath + File.separator + filename;
        File dest = new File(filePath);
        file.transferTo(dest);

        // Set the URL or path to the stored video file
        video.setUrl(filePath); // You can adjust the URL structure as needed

        // Save the video metadata to the database
        return videoRepository.save(video);
    }

    public Optional<Video> getVideoById(Long id) {
        return videoRepository.findById(id);
    }

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    @Override
    public List<Video> findByTimestampAfter(LocalDateTime timeStamp) {
        return videoRepository.findByTimestampAfter(timeStamp);
    }

    @Override
    public List<Video> findByFileName(String fileName) {
        return videoRepository.findByFilename(fileName);
    }


}

