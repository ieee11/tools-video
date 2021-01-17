package com.video.service;

import com.video.Response.MultipartFileResponse;

public interface VideoService {

    /**
     * 捕获视频帧
     *
     * @param videoPath 视频路径
     * @param frameNum  要捕获的视频帧的数值
     * @return com.video.Response.MultipartFileResponse
     * @Author yanxu
     * @Description 捕获视频帧
     * @Date 2021/1/16 17:54
     **/
    MultipartFileResponse captureVideoFrame(String videoPath, Integer frameNum) throws Exception;

}
