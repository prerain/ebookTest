package com.example.ebook01.network;

import android.util.ArrayMap;

import java.util.Map;

public class LoginRequest extends EbookRequestTask {
    String username;
    String password;
    public LoginRequest(String username,String password){
        this.username = username;
        this.password = password;
    }
    @Override
    public String getRefUrl() {
        return "/app/login";
    }

    @Override
    public Map<String,Object> getRequestBody() {
        ArrayMap<String, Object> paramsBody = new ArrayMap<>();
        paramsBody.put("username",username);
        return paramsBody;
    }
}
