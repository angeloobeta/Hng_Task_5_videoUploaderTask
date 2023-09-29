package com.example.hng_task_5_videouploadertask.services.video;

import com.example.hng_task_5_videouploadertask.data.dto.payload.VideoRequestDto;
import com.example.hng_task_5_videouploadertask.data.dto.response.ApiResponseDto;
import com.example.hng_task_5_videouploadertask.data.dto.response.VideoResponseDto;
import com.example.hng_task_5_videouploadertask.data.entity.Video;
import com.example.hng_task_5_videouploadertask.data.repositories.VideoRepository;
import com.example.hng_task_5_videouploadertask.exceptions.VideoException;
import com.example.hng_task_5_videouploadertask.services.cloud.CloudService;
import com.example.hng_task_5_videouploadertask.utils.VideoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements  VideoService{

    private final VideoRepository videoRepository;
    private final VideoUtils videoUtils;
    private final CloudService cloudService;

//    @Value("${video.upload.path}") // Configure the path where videos will be saved on the file system
    private String uploadPath;

    @Transactional
    public ApiResponseDto<List<VideoResponseDto>> uploadVideo(  MultipartFile[] file) throws IOException {

        //
        List<VideoResponseDto> videoResponseDtoList = new ArrayList<>();
        for(MultipartFile multipartFile : file){
            String filename = cloudService.uploadFile(multipartFile);
            // Generate a unique filename for the video
        String uploadedFileUrl = LocalDateTime.now() + "_" + StringUtils.cleanPath(filename);


            // Save the video metadata to the database
            Video videoData = Video.builder()
                    .fileSize(String.valueOf(multipartFile.getSize()))
                    .filename(multipartFile.getName())
                    .timestamp(LocalDateTime.now())
                    .fileUrl(uploadedFileUrl)
                    .build();
            Video videoUploaded = videoRepository.save(videoData);
            VideoResponseDto videoResponseDto = VideoResponseDto.builder()
                    .downloadUrl(videoUploaded.getFileUrl())
                    .timeStamp(videoUploaded.getTimestamp())
                    .fileName(videoUploaded.getFilename())
                    .fileSize(videoUploaded.getFileSize())
                    .build();

            assert false;
            videoResponseDtoList.add(videoResponseDto);
        }


        return new ApiResponseDto<>("Upload successfully", 200, videoResponseDtoList);
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

