package com.phoenix.phoenix.service.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class CloudinaryServiceImplTest {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private Cloudinary cloudinary;

    @Test
    void cloudinaryObjectInstanceTest(){
        assertThat(cloudinary).isNotNull();
    }


    @Test
    void uploadToCloudinaryTest() throws IOException {
        File file = new File("src/test/resources/Gideon and Nonso and CJ.jpg");
        log.info("File is :: {}", file);
        assertThat(file.exists()).isTrue();
        Map<?,?> uploadResult = cloudinaryService.upload(Files.readAllBytes(file.toPath()), ObjectUtils.asMap("overwrite",true));
        log.info("Upload result is :: {}",uploadResult);
        assertThat(uploadResult.get("url")).isNotNull();
    }

    @Test
    public void uploadMultipartFileToCloudinaryTest()throws IOException{
        //To upload a file to cloudinary as a multipart file
        Path path = Paths.get("src/test/resources/Gideon and Nonso and CJ.jpg");
        assertTrue(path.toFile().exists());
        assertThat(path.getFileName().toString()).isEqualTo("Gideon and Nonso and CJ.jpg");
        //convert to multipart
        MultipartFile multipartFile = new MockMultipartFile(
                path.getFileName().toString(),
                path.getFileName().toString(),
                "img/png",
                Files.readAllBytes(path));

        assertThat(multipartFile).isNotNull();
        assertThat(multipartFile.isEmpty()).isFalse();
        //upload to cloud
        cloudinaryService.upload(multipartFile.getBytes(), ObjectUtils.asMap("overwrite",true));

    }
}