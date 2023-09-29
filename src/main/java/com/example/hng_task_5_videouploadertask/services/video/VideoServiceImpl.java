package com.example.hng_task_5_videouploadertask.services.video;

import com.example.hng_task_5_videouploadertask.data.dto.payload.VideoRequestDto;
import com.example.hng_task_5_videouploadertask.data.dto.response.ApiResponseDto;
import com.example.hng_task_5_videouploadertask.data.dto.response.VideoResponseDto;
import com.example.hng_task_5_videouploadertask.data.entity.Video;
import com.example.hng_task_5_videouploadertask.data.repositories.VideoRepository;
import com.example.hng_task_5_videouploadertask.exceptions.VideoException;
import com.example.hng_task_5_videouploadertask.utils.VideoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements  VideoService{

    private final VideoRepository videoRepository;
    private final VideoUtils videoUtils;

//    @Value("${video.upload.path}") // Configure the path where videos will be saved on the file system
    private String uploadPath;

    @Transactional
    public ApiResponseDto<VideoResponseDto> uploadVideo(MultipartFile file) throws IOException {
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
        video.setFileUrl(filePath); // You can adjust the URL structure as needed

        // Save the video metadata to the database
        Video videoData = Video.builder()
                .fileSize(String.valueOf(file.getSize()))
                .filename(file.getName())
                .timestamp(LocalDateTime.now())
                .fileUrl(file.getOriginalFilename())
                .build();
        Video videoUploaded = videoRepository.save(videoData);
        VideoResponseDto videoRequestDto = VideoResponseDto.builder()
                .downloadUrl(videoUploaded.getFileUrl())
                .timeStamp(videoUploaded.getTimestamp())
                .fileName(videoUploaded.getFilename())
                .fileSize(videoUploaded.getFileSize())
                .build();

        return new ApiResponseDto<>("Upload successfully", 200, videoRequestDto);
    }

    public ApiResponseDto<VideoResponseDto> getVideoById(String id) {
        Video response = videoRepository.findById(id)
                .orElseThrow(() -> new VideoException("Video doesn't exist"));
//        VideoResponseDto videoResponse = VideoResponseDto.builder()
//                .fileSize(response.getFileSize())
//                .downloadUrl(response.getFileUrl())
//                .fileId(response.getId())
//                .fileName(response.getFilename())
//                .timeStamp(response.getTimestamp())
//                .build();
        return new ApiResponseDto<>("Success", 200, videoUtils.mapVideoToDto(response));
    }

    public ApiResponseDto<List<VideoResponseDto>> getAllVideos() {
        List<VideoResponseDto> response = videoRepository.findAll()
                .stream()
                .map(video -> videoUtils.mapVideoToDto(video)).toList();
        return new ApiResponseDto<>("All video successfully fetched", 200, response);
    }

    @Override
    public ApiResponseDto<List<VideoResponseDto>> findByTimestampAfter(LocalDateTime timeStamp) {
        List<VideoResponseDto> response = videoRepository.findByTimestampAfter(timeStamp)
                .stream()
                .map(videoUtils::mapVideoToDto).toList();
        return new ApiResponseDto<>("Fetch successfully", 200, response);
    }

    @Override
    public ApiResponseDto<List<VideoResponseDto>> findByFileName(String fileName) {
        List<VideoResponseDto> response = videoRepository.findByFilename(fileName)
                .stream()
                .map(video -> videoUtils.mapVideoToDto(video)).toList();
        return new ApiResponseDto<>("Fetch successfully", 200, response);
    }


}

