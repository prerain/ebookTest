package com.example.ebook01.network;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public enum OkhttpClientManager {
    INSTANCE;
    OkHttpClient okHttpClient = new OkHttpClient();
    public Response executeRequest(IRequestTask requestTask) throws IOException {
        if (requestTask == null) return null;
        Map<String, Object> requestBody = requestTask.getRequestBody();
        FormBody.Builder builder = new FormBody.Builder();
        if (requestBody != null && !requestBody.isEmpty()){
            Set<Map.Entry<String, Object>> entries = requestBody.entrySet();
            for (Map.Entry<String,Object> entry : entries){
                if (entry == null) continue;
                builder.add(entry.getKey(),entry.getValue() + "");
            }
        }

        FormBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(requestTask.getBaseUrl() + requestTask.getRefUrl())
                .post(formBody)
                .build();
        return okHttpClient.newCall(request).execute();
    }
}
