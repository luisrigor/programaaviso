package com.gsc.programaavisos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ProgramaavisoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProgramaavisoApplication.class, args);
    }

}
