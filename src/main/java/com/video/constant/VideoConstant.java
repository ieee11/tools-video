package com.video.constant;

import org.springframework.stereotype.Component;

/**
 * @ClassName VideoConstant
 * @Description 常量类
 * @Author Yanxu
 * @Date 2021/1/16 18:49
 * @Version 1.0
 **/
@Component
public final class VideoConstant {

    public final static String VIDEO_META_DATA_ROTATE = "rotate";

    public final static String IMAGE_FORMAT_NAME_JPG = "jpg";

    public final static String FORMAT_NAME_FILE = "file";

    public final static String FORMAT_NAME_FILE_JPG = FORMAT_NAME_FILE + "." + IMAGE_FORMAT_NAME_JPG;

    public final static String FORMAT_NAME_CONTENT_TYPE = "text/plain";

}
