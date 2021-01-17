package com.video.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * @ClassName VideoProperties
 * @Description Video 属性类
 * @Author Yanxu
 * @Date 2021/1/16 18:57
 * @Version 1.0
 **/
@ConfigurationProperties(prefix = "com.video")
public class VideoProperties implements Serializable {

    private String frameNum = "5";

    public String getFrameNum() {
        return frameNum;
    }

    public void setFrameNum(String frameNum) {
        this.frameNum = frameNum;
    }
}
