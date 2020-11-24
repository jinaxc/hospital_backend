package com.jinax.hospital_management_backend;

import com.jinax.hospital_management_backend.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HospitalManagementBackendApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void contextLoads() {
        userRepository.findAll();
    }

}
