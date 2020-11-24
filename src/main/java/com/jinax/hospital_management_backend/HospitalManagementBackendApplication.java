package com.jinax.hospital_management_backend;

import com.jinax.hospital_management_backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HospitalManagementBackendApplication {


    public static void main(String[] args) {
        SpringApplication.run(HospitalManagementBackendApplication.class, args);
    }

}
