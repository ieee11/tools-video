package com.video.Response;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Objects;

/**
 * @ClassName MultipartFileResponse
 * @Description MultipartFile Response 类
 * @Author Yanxu
 * @Date 2021/1/16 18:40
 * @Version 1.0
 **/
public class MultipartFileResponse implements Serializable {

    private MultipartFile multipartFile;

    public MultipartFileResponse() {

    }

    public MultipartFileResponse(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultipartFileResponse that = (MultipartFileResponse) o;
        return multipartFile.equals(that.multipartFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(multipartFile);
    }

    @Override
    public String toString() {
        return "MultipartFileResponse{" +
                "multipartFile=" + multipartFile +
                '}';
    }
}
