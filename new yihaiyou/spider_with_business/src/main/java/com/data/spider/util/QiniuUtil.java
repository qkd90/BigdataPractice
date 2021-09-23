package com.data.spider.util;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Created by guoshijie on 2015/7/6.
 */
public class QiniuUtil {

    private static final Logger log = Logger.getLogger(QiniuUtil.class);

    private static final String ACCESS_KEY = "Y1dp2QzckTnnOpClgOge_EkNwgHXOabV64hGSlCI";
    private static final String SECRET_KEY = "zOyIqSCo37C1h19FYk-x0oJGIT0r_JC5xSIaAflS";

    private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);


    // 简单上传，使用默认策略
    private String getUpToken0() {
        return auth.uploadToken("bucket");
    }

    // 覆盖上传
    private String getUpToken1() {
        return auth.uploadToken("bucket", "key");
    }

    // 设置指定上传策略
    private String getUpToken2() {
        return auth.uploadToken("bucket", null, 3600, new StringMap()
                .put("callbackUrl", "call back url").putNotEmpty("callbackHost", "")
                .put("callbackBody", "key=$(key)&hash=$(etag)"));
    }

    // 设置预处理、去除非限定的策略字段
    private String getUpToken3() {
        return auth.uploadToken("bucket", null, 3600, new StringMap()
                .putNotEmpty("persistentOps", "").putNotEmpty("persistentNotifyUrl", "")
                .putNotEmpty("persistentPipeline", ""), true);
    }

    /**
     * 生成上传token
     *
     * @param bucket  空间名
     * @param key     key，可为 null
     * @param expires 有效时长，单位秒。默认3600s
     * @param policy  上传策略的其它参数，如 new StringMap().put("endUser", "uid").putNotEmpty("returnBody", "")。
     * scope通过 bucket、key间接设置，deadline 通过 expires 间接设置
     * @param strict  是否去除非限定的策略字段，默认true
     * @return 生成的上传token
     */


    private static UploadManager uploadManager = new UploadManager();

    public static QiniuResult upload(byte[] file, String name) {
        try {
            Response res = uploadManager.put(file, name, getUpToken(name));
            QiniuResult result = res.jsonToObject(QiniuResult.class);
            log.info("上传成功");
            return result;
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时简单状态信息
            log.error(r.toString());
            try {
                // 响应的文本信息
                log.error(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
        return null;
    }

    public static QiniuResult upload(File file, String name) {
        try {
            Response res = uploadManager.put(file, name, getUpToken(name));
            QiniuResult result = res.jsonToObject(QiniuResult.class);
            return result;
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时简单状态信息
            log.error(r.toString());
            try {
                // 响应的文本信息
                log.error(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        }
        return null;
    }

    /**
     * 七牛云存储常规上传公用方法
     *
     * @param imageUrl
     * @return
     */
    public static String UploadToQiniu(String imageUrl) {
        byte[] bytes = downloadBytes(imageUrl);
        if (bytes == null) {
            return null;
        }
        String filename = imageUrl;
        String suffix = "";
        if (StringUtils.isNotBlank(filename)) {
            String[] nameArr = filename.split("\\.");
            suffix = "." + nameArr[nameArr.length - 1];
        }
        String path = "transportation/" + UUIDUtil.getUUID() + suffix;
        QiniuUtil.upload(bytes, path);
        return path;
    }

    private static byte[] downloadBytes(String sourceUrl) {
        HttpGet httpGet = new HttpGet(sourceUrl);
        HttpResponse response;
        HttpClient httpClient = HttpClientUtils.getHttpClient();
        ////抓包检查调试
//      HttpHost proxy = new HttpHost("127.0.0.1", 8888);
//      httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        try {
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (iPad; CPU OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B176 Safari/7534.48.3");
            response = httpClient.execute(httpGet);
            return EntityUtils.toByteArray(response.getEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getUpToken(String key) {
        return auth.uploadToken("hmlyinfo", key, 3600, new StringMap()
                .putNotEmpty("returnBody", "{\"key\": $(key), \"name\": $(fname), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height)}"));
    }

    public static void main(String[] args) {
//      QiniuUtil qiniuService = new QiniuUtil();
//      qiniuService.upload(new File("f:\\20150423192754.jpg"), "/user/face/11.png");
        QiniuUtil.upload(new File("f:\\20150423192754.jpg"), "/user/face/11.png");
    }


    public static String UploadToQiniu(String imageUrl, String dir) {
        byte[] bytes = downloadBytes(imageUrl);
        if (bytes == null) {
            return null;
        }
        String filename = imageUrl;
        String suffix = "";
        if (StringUtils.isNotBlank(filename)) {
            String[] nameArr = filename.split("\\.");
            suffix = "." + nameArr[nameArr.length - 1];
        }
        String path = dir + "/" + UUIDUtil.getUUID() + suffix;
        QiniuUtil.upload(bytes, path);
        return path;
    }

    public static String UploadToQiniu(String imageUrl, String dir, String secondDir) {
        byte[] bytes = downloadBytes(imageUrl);
        if (bytes == null) {
            return null;
        }
        String filename = imageUrl;
        String suffix = "";
        if (StringUtils.isNotBlank(filename)) {
            String[] nameArr = filename.split("\\.");
            suffix = "." + nameArr[nameArr.length - 1];
        }
        String path = dir + "/" + secondDir + "/" + UUIDUtil.getUUID() + suffix;
        QiniuUtil.upload(bytes, path);
        return path;
    }

//    private static byte[] downloadBytes(String sourceUrl) {
//        HttpGet httpGet = new HttpGet(sourceUrl);
//        HttpResponse response;
//        HttpClient httpClient = HttpClientUtils.getHttpClient();
//        try {
//            httpGet.setHeader("User-Agent", "Mozilla/5.0 (iPad; CPU OS 5_1 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B176 Safari/7534.48.3");
//            response = httpClient.execute(httpGet);
//            return EntityUtils.toByteArray(response.getEntity());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}
