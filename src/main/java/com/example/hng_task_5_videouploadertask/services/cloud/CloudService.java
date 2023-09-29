package com.example.hng_task_5_videouploadertask.services.cloud;

import org.springframework.web.multipart.MultipartFile;

public interface CloudService {

    String uploadFile(MultipartFile multipartFile);

}
