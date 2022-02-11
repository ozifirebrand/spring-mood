package com.phoenix.phoenix.service.cloud;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {
    Map<?, ?> upload(File file, Map<?,?> params) throws IOException;
    Map<?, ?> upload(MultipartFile file, Map<?,?> params) throws IOException;
}
