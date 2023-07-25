package com.example.ebook01.network;

import java.util.Map;

public interface IRequestTask {
    String getBaseUrl();
    String getRefUrl();

    Map<String,Object> getRequestBody();
}
