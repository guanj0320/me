package me.guanjun.web.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by guanj on 2014/5/4.
 */
@JsonIgnoreProperties({"bytes"})
public class FileMeta {
    private String fileUrl;
    private String sfileUrl;//增加用于存放小图片的URL地址
    private String fileName;
    private String sfileName;//增加用于存放小图片文件地址
    private String fileSize;
    private String fileType;

    private byte[] bytes;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getSfileName() {
        return sfileName;
    }

    public void setSfileName(String sfileName) {
        this.sfileName = sfileName;
    }

    public String getSfileUrl() {
        return sfileUrl;
    }

    public void setSfileUrl(String sfileUrl) {
        this.sfileUrl = sfileUrl;
    }
}
