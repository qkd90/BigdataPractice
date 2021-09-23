package com.data.spider.util;

/**
 * Created by Sane on 16/1/26.
 */
public class qiniuImageInfo {

    /**
     * format : jpeg
     * width : 755
     * height : 495
     * colorModel : ycbcr
     */

    private String format;
    private int width;
    private int height;
    private String colorModel;

    public void setFormat(String format) {
        this.format = format;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setColorModel(String colorModel) {
        this.colorModel = colorModel;
    }

    public String getFormat() {
        return format;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getColorModel() {
        return colorModel;
    }
}