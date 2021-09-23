import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.framework.hibernate.util.Page;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.QiniuUtil;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 七牛图片水印初始化处理
 * Created by caiys on 2017/1/25.
 */
@Ignore
public class QiniuWatermarkInit extends TestCase {

    private final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext*.xml");
    private final ProductimageService productimageService = (ProductimageService) applicationContext.getBean("productimageService");
    private final PropertiesManager propertiesManager = (PropertiesManager) applicationContext.getBean("propertiesManager");

    @Override
    protected void tearDown() throws Exception {
        ((ClassPathXmlApplicationContext) applicationContext).close();
    }

    /**
     * 平台已维护的民宿、海上休闲图片水印处理
     */
    public void testInit() {
        String downloadDir = "D:/qiniudownload";
        String watermarkOps = propertiesManager.getString("WATERMARK_OPS");
        Integer pageIndex = 1;
        Integer pageSize = 20;
        Page page = null;
        while (true) {
            page = new Page(pageIndex, pageSize);
            // 查询需要处理的图片
            List<Productimage> list = productimageService.listImagesForQiniu(page);
            if (list.size() <= 0) {
                break;
            }

            // 循环处理图片
            for (Productimage i : list) {
                // 如果图片已经处理过，跳过当前循环
                String filePath = i.getPath();
                if (filePath.indexOf(QiniuUtil.WATERMARK_SUFFIX) > -1) {
                    continue;
                }
                try {
                    String preffix = filePath.substring(0, filePath.lastIndexOf("."));
                    String suffix = filePath.substring(filePath.lastIndexOf("."));
                    String fileFullPath = QiniuUtil.URL + filePath;
                    downLoadFromUrl(fileFullPath, i.getId().toString() + suffix, downloadDir);
                    File file = new File(downloadDir + File.separator + i.getId().toString() + suffix);
                    String newFilePath = preffix + QiniuUtil.WATERMARK_SUFFIX + suffix.toLowerCase();
                    QiniuUtil.upload(file, filePath, watermarkOps, newFilePath);
                    i.setPath(newFilePath);
                    productimageService.update(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 已经是最后一页数据
            if (!page.getHasNext()) {
                break;
            }
            pageIndex++;
        }

    }

    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public void downLoadFromUrl(String urlStr, String fileName, String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos!=null) {
            fos.close();
        }
        if (inputStream!=null) {
            inputStream.close();
        }
        System.out.println("info:"+url+" download success");
    }

    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }

}
