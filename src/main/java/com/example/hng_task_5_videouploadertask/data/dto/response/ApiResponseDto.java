package com.example.hng_task_5_videouploadertask.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @Builder @AllArgsConstructor
public class ApiResponseDto<T> {
    private String message;
    private Integer statusCode;
    private T data;
}
