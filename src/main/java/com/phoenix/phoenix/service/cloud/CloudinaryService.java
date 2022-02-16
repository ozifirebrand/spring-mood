package com.phoenix.phoenix.service.cloud;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {
    Map<?, ?> upload(byte [] bytes, Map<?,?> params) throws IOException;

}
