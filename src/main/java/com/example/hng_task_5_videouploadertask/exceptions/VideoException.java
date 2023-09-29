package com.example.hng_task_5_videouploadertask.exceptions;

import com.example.hng_task_5_videouploadertask.data.dto.response.VideoResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class VideoException extends RuntimeException {
    private  HttpStatus status = HttpStatus.BAD_REQUEST;

    public VideoException(){
        this("Error Proccesing Request!");
    }

    public VideoException(String message){
        super(message);
    }

    public VideoException(String message, HttpStatus status){
        this(message);
        this.status = status;
    }
}