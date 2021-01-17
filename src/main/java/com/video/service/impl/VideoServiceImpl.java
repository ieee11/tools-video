package com.video.service.impl;

import com.video.Response.MultipartFileResponse;
import com.video.config.VideoProperties;
import com.video.constant.VideoConstant;
import com.video.service.VideoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName VideoServiceImpl
 * @Description 视频处理 Service 类
 * @Author Yanxu
 * @Date 2021/1/16 17:54
 * @Version 1.0
 **/
@Service
public class VideoServiceImpl implements VideoService {

    private final static Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    private Integer frameNum;

    public VideoServiceImpl() {

    }

    public VideoServiceImpl(VideoProperties videoProperties) {
        this.frameNum = Integer.parseInt(videoProperties.getFrameNum());
    }

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
    @Override
    public MultipartFileResponse captureVideoFrame(String videoPath, Integer frameNum) throws Exception {
        if (null == frameNum) {
            frameNum = this.frameNum;
        }
        if (StringUtils.isBlank(videoPath)) {
            return null;
        }
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(videoPath);
        ff.start();
        String rotate = ff.getVideoMetadata(VideoConstant.VIDEO_META_DATA_ROTATE);
        Frame f;
        int i = 0;
        MultipartFileResponse multipartFileResponse = null;
        while (frameNum >= i) {
            f = ff.grabImage();
            IplImage src = null;
            if (null != rotate && rotate.length() > 1) {
                OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
                src = converter.convert(f);
                f = converter.convert(rotate(src, Integer.parseInt(rotate)));
            }
            if (frameNum == i && f.image != null) {
                multipartFileResponse = doExecuteFrame(f);
            }
            i++;
        }
        ff.stop();
        return multipartFileResponse;
    }

    /*
     * 旋转角度的
     */
    private IplImage rotate(IplImage src, int angle) {
        IplImage img = IplImage.create(src.height(), src.width(), src.depth(), src.nChannels());
        opencv_core.cvTranspose(src, img);
        opencv_core.cvFlip(img, img, angle);
        return img;
    }

    private MultipartFileResponse doExecuteFrame(Frame f) {
        if (null == f || null == f.image) {
            return null;
        }
        BufferedImage bi = new Java2DFrameConverter().getBufferedImage(f);
        logger.info("width: {} , height: {}", bi.getWidth(), bi.getHeight());
        MultipartFileResponse multipartFileResponse = getPath(bi);
        logger.info(multipartFileResponse.toString());
        return multipartFileResponse;
    }

    private MultipartFileResponse getPath(BufferedImage bufferedImage) {
        //创建一个ByteArrayOutputStream
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //把BufferedImage写入ByteArrayOutputStream
        try {
            ImageIO.write(bufferedImage, VideoConstant.IMAGE_FORMAT_NAME_JPG, os);
        } catch (IOException e) {
            logger.info("ImageIO Write IOException : {}", e);
        }
        //ByteArrayOutputStream转成InputStream
        InputStream input = new ByteArrayInputStream(os.toByteArray());
        //InputStream转成MultipartFile
        MultipartFile multipartFile = null;
        try {
            multipartFile = new MockMultipartFile(VideoConstant.FORMAT_NAME_FILE, VideoConstant.FORMAT_NAME_FILE_JPG, VideoConstant.FORMAT_NAME_CONTENT_TYPE, input);
        } catch (IOException e) {
            logger.info("MockMultipartFile IOException : {}", e);
        }
        return new MultipartFileResponse(multipartFile);
    }
}
