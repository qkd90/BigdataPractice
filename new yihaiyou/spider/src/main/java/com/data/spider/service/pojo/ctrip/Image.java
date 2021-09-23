package com.data.spider.service.pojo.ctrip;

import org.joda.time.DateTime;

/**
 * Created by Sane on 15/9/15.
 */
public class Image {
    public int Id;
    public int SourceWidth;
    public int SourceHeight;
    public String SourceUrl;
        public Imagesizemap[] ImageSizeMap ;
//    public DateTime LastUpdateTime;
    public String Title;
//    public DateTime CreateTime;
//    public String DataSource;
//    public Boolean HasCopyright;
//    public int UserId;
    public String Nickname;
//    public String Author;
//    public String ClientAuth;

    public class Imagesizemap {
        public String Key;
        public String ImageUrl;
    }
}
