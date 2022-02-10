package com.phoenix.phoenix.service.cloud;

import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class CloudinaryServiceImplTest {

    @Autowired
    @Qualifier("cloudinary")
    private CloudinaryService cloudinaryService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void uploadToCloudinaryTest() {
        File file = new File("src/test/resources/Gideon and Nonso and CJ.jpg");
        log.info("File is :: {}", file);
        assertThat(file).isNotNull();
        Map<?,?> uploadResult = cloudinaryService.upload(file, ObjectUtils.emptyMap());
        log.info("Upload result is :: {}",uploadResult);
        assertThat(uploadResult).isNotNull();

    }
}