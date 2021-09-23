package com.zuipin.util;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import org.apache.log4j.Logger;

import java.io.File;

/**
 * Created by guoshijie on 2015/7/6.
 */
public class QiniuUtil {

    private static final Logger LOG = Logger.getLogger(QiniuUtil.class);
    private static String accessKey = null;
    private static String secretKey = null;
    private static String bucketName = null;
    public static String URL = null;
    public static final String WATERMARK_SUFFIX = "_yhy";

    private static Auth auth = null;
    private static Zone z = Zone.zone0();
    private static Configuration c = new Configuration(z);

    static {
        try {
            PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
            accessKey = propertiesManager.getString("QINIU_ACCESS_KEY");
            secretKey = propertiesManager.getString("QINIU_SECRET_KEY");
            bucketName = propertiesManager.getString("QINIU_BUCKET_NAME");
            URL = propertiesManager.getString("QINIU_BUCKET_URL");
            auth = Auth.create(accessKey, secretKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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


    private static UploadManager uploadManager = new UploadManager(c);

    public static QiniuResult upload(byte[] file, String name) {
        try {
            Response res = uploadManager.put(file, name, getUpToken(name));
            QiniuResult result = res.jsonToObject(QiniuResult.class);
            LOG.info("上传成功");
            return result;
        } catch (QiniuException e) {
            processQiniuException(e);
        }
        return null;
    }

    private static void processQiniuException(QiniuException e) {
        Response r = e.response;
        // 请求失败时简单状态信息
        LOG.error(r.toString());
        try {
            // 响应的文本信息
            LOG.error(r.bodyString());
        } catch (QiniuException e1) {
            //ignore
        }
    }

    public static QiniuResult upload(File file, String name) {
        return upload(file, name, null, null);
    }


    public static String getUpToken(String key) {
        return auth.uploadToken(bucketName, key, 3600, new StringMap()
                .putNotEmpty("returnBody", "{\"key\": $(key), \"name\": $(fname), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height)}"));
    }

    /**
     * @param watermarkOps - 水印样式及地址相关配置
     * @param watermarkName - 水印的真实文件名称
     * @return
     */
    public static QiniuResult upload(File file, String name, String watermarkOps, String watermarkName) {
        try {
            Response res = null;
            if (StringUtils.isNotBlank(watermarkOps)) {
                res = uploadManager.put(file, name, getUpToken(watermarkName, watermarkOps));
            } else {
                res = uploadManager.put(file, name, getUpToken(name));
            }
            QiniuResult result = res.jsonToObject(QiniuResult.class);
            return result;
        } catch (QiniuException e) {
            processQiniuException(e);
        }
        return null;
    }

    /**
     * @param watermarkOps - 水印样式及地址相关配置
     * @return
     */
    public static String getUpToken(String key, String watermarkOps) {
        String urlbase64 = UrlSafeBase64.encodeToString(bucketName + ":" + key);
        watermarkOps = watermarkOps + "|saveas/" + urlbase64;   // 可以对转码后的文件进行使用saveas参数自定义命名，当然也可以不指定文件会默认命名并保存在当前空间。
        return auth.uploadToken(bucketName, null, 3600, new StringMap()
                .putNotEmpty("returnBody", "{\"key\": $(key), \"name\": $(fname), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height)}")
                .putNotEmpty("persistentOps", watermarkOps)
                .putNotEmpty("persistentPipeline", "pipeline01"));    // 设置转码的队列，与七牛平台设置对应
    }

    public static void delete(String fileName) {

        //实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth, c);
        //要测试的空间和key，并且这个key在你空间中存在
        String bucket = bucketName;
        String key = fileName;
        try {
            //调用delete方法移动文件
            bucketManager.delete(bucket, key);
        } catch (QiniuException e) {
            //捕获异常信息
            Response r = e.response;
            System.out.println(r.toString());

        }
    }

   /* public static void main(String[] args) {
        QiniuUtil qiniuService = new QiniuUtil();

        qiniuService.delete(bucketName, "contract/appendice/1479200474865.xlsx");
    }
*/
}
