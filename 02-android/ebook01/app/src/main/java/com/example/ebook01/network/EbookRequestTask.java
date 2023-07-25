package com.example.ebook01.network;

import com.example.ebook01.utils.Httpurl;

public abstract class EbookRequestTask implements IRequestTask{
    @Override
    public String getBaseUrl() {
        return Httpurl.getUrl();
    }
}
