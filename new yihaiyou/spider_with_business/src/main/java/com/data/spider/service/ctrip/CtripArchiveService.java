package com.data.spider.service.ctrip;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sane on 15/12/18.
 * 携程网页快照，
 * ；
 */
public class CtripArchiveService {

    //此处替换为archive文件的路径
    public static String archiveApi = "/Users/Sane/Downloads/web/%d_%d_%s_%d.html";

    public static String getAdviceTime(int cityCode, String cityPy, int cityId, int ctripId) {
        String url = String.format(archiveApi, cityCode, cityId, cityPy, ctripId);
        try {
            File file = new File(url);
            if (!file.exists()) {
                return null;
            }
            FileReader fr = new FileReader(file);
            int fileLen = (int) file.length();
            char[] chars = new char[fileLen];
            fr.read(chars);
            String txt = String.valueOf(chars);
            Pattern p = Pattern.compile(
                    "游玩时间：[\\w\\W]*?(建议\\d+.*[<$\\r\\n])", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(txt);
            if (m.find()) {
                return m.group(1).replace("建议", "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
    }
}
