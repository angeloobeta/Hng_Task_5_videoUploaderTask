package com.example.hng_task_5_videouploadertask.services.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@Service
@AllArgsConstructor
public class CloudinaryServiceImpl implements CloudService {

    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            Map<?, ?> response = cloudinary.uploader().uploadLarge(file.getBytes(), ObjectUtils.emptyMap());
            System.out.println("Uploaded file now");
            return response.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
