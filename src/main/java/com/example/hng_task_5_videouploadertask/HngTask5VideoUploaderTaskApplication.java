package com.example.hng_task_5_videouploadertask;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@Slf4j
@OpenAPIDefinition(
        info =
        @io.swagger.v3.oas.annotations.info.Info(
                description = "This app provides stores video files",
                title = "Video Backend App",
                version = "1.0"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8000",
                        description = "DEV Server"
                ),
                @Server(
                        url = "https://hng-video-servers.onrender.com",
                        description = "PROD server"
                )
        }
        )

@EnableAsync
public class HngTask5VideoUploaderTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(HngTask5VideoUploaderTaskApplication.class, args);
        log.info("::::::Server Running::::::");

    }

}
