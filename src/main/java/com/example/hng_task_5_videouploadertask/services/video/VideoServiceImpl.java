package com.example.hng_task_5_videouploadertask.services.video;

import com.example.hng_task_5_videouploadertask.data.dto.response.ApiResponseDto;
import com.example.hng_task_5_videouploadertask.data.dto.response.VideoResponseDto;
import com.example.hng_task_5_videouploadertask.data.entities.Video;
import com.example.hng_task_5_videouploadertask.data.repositories.VideoRepository;
import com.example.hng_task_5_videouploadertask.exceptions.VideoException;
import com.example.hng_task_5_videouploadertask.utils.VideoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.hng_task_5_videouploadertask.utils.VideoUtils.saveVideoToDisk;


@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements  VideoService{

    private final VideoRepository videoRepository;
    private final VideoUtils videoUtils;

    @Transactional
    public ApiResponseDto<List<VideoResponseDto>> uploadVideo(MultipartFile[] UploadedVideos) throws IOException {


        List<VideoResponseDto> videoResponseDtoList = new ArrayList<>();
        String savedVideoFilePath = null;

        // Save video to disk
        for(MultipartFile multipartFile : UploadedVideos){
            System.out.println("Uploading : "+ multipartFile.getOriginalFilename() + "to the server ");
            try {
                savedVideoFilePath = saveVideoToDisk(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("An Error occurred while writing video file to disk");
            }

            // Generate a unique filename for the video
            assert savedVideoFilePath != null;
            String uploadedFileUrl = StringUtils.cleanPath(savedVideoFilePath);

        // Transcribe the video
            String videoTranscription = VideoUtils.transcribeVideo(savedVideoFilePath);

            // Save the video metadata to the database
            Video videoData = Video.builder()
                    .fileSize(String.valueOf(multipartFile.getSize()))
                    .filename(multipartFile.getOriginalFilename())
                    .timestamp(LocalDateTime.now())
                    .transcriptionText(videoTranscription)
                    .fileUrl(uploadedFileUrl)
                    .build();
            Video videoUploaded = videoRepository.save(videoData);
            VideoResponseDto videoResponseDto = VideoResponseDto.builder()
                    .fileUrl(videoUploaded.getFileUrl())
                    .fileId(String.valueOf(videoUploaded.getId()))
                    .timeStamp(videoUploaded.getTimestamp())
                    .fileName(videoUploaded.getFilename())
                    .fileSize(videoUploaded.getFileSize())
                    .transcription(videoTranscription)
                    .build();

            videoResponseDtoList.add(videoResponseDto);
        }


        return new ApiResponseDto<>("Upload successfully", 200,videoResponseDtoList);
    }

    @Override
    public ApiResponseDto<Optional<Video>> findVideoById(String fileId) {

        if(videoRepository.findVideoById(fileId).isPresent())
        {
            return new ApiResponseDto<>("Success", 200, videoRepository.findVideoById(fileId));
        }else {
            return new ApiResponseDto<>("Video doesn't exist", 200, null);
        }
//        VideoResponseDto videoResponse = VideoResponseDto.builder()
//                .fileSize(response.getFileSize())
//                .downloadUrl(response.getFileUrl())
//                .fileId(response.getId())
//                .fileName(response.getFilename())
//                .timeStamp(response.getTimestamp())
//                .build();
    }

    @Override
    public ApiResponseDto<List<VideoResponseDto>> getAllVideos() {
        List<VideoResponseDto> response = videoRepository.findAll()
                .stream()
//                .map(video -> videoUtils.mapVideoToDto(video)).toList();
                .map(videoUtils::mapVideoToDto).toList();
        return new ApiResponseDto<>("All video successfully fetched", 200, response);
    }

    @Override
    public ApiResponseDto<List<VideoResponseDto>> findByFileName(String fileName) {
        List<VideoResponseDto> response = videoRepository.findByFilename(fileName)
                .stream()
//                .map(video -> videoUtils.mapVideoToDto(video)).toList();
                .map(videoUtils::mapVideoToDto).toList();
        return new ApiResponseDto<>("Fetch successfully", 200, response);
    }

    @Override
    public ApiResponseDto<String> deleteFileById(String fileId) {
       ;
        if(videoRepository.findVideoById(fileId).isPresent())
        {
            videoRepository.deleteById(fileId);
            return new ApiResponseDto<>("Deleted successfully", 200, null);
        }else {
            return new ApiResponseDto<>("Fails doesn't exist", 200, null);
        }
    }


}

