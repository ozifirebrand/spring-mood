package com.phoenix.phoenix.service.cloud;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
@Service
public class CloudinaryServiceImpl implements CloudinaryService{
    @Autowired
    private Cloudinary cloudinary;
    @Override
    public Map<?, ?> upload(File file, Map<?, ?> params) throws IOException {

        return cloudinary.uploader().upload(Files.readAllBytes(file.toPath()), params);
    }

    @Override
    public Map<?, ?> upload(MultipartFile file, Map<?, ?> params) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(), params);
    }
}
