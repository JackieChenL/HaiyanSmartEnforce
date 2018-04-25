package com.kas.clientservice.haiyansmartenforce.Http;

public interface OnProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}