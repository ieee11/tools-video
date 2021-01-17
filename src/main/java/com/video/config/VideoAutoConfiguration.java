package com.video.config;

import com.video.service.VideoService;
import com.video.service.impl.VideoServiceImpl;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName VideoAutoConfiguration
 * @Description 自动装配的配置类
 * @Author Yanxu
 * @Date 2021/1/16 19:00
 * @Version 1.0
 **/
@Configuration
//@ConditionalOnClass({FFmpegFrameGrabber.class, OpenCVFrameConverter.class, Java2DFrameConverter.class})
@EnableConfigurationProperties(VideoProperties.class)
public class VideoAutoConfiguration {

    @Autowired
    private VideoProperties videoProperties;

    @Bean
    VideoService videoService() {
        return new VideoServiceImpl(videoProperties);
    }

}
