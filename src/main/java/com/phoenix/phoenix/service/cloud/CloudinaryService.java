package com.phoenix.phoenix.service.cloud;

import java.io.File;
import java.util.Map;

public interface CloudinaryService {
    Map<?, ?> upload(File file, Map<?,?> params);
}
