package com.zuipin.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.zuipin.util.StaticConstants.BACKUP;
import static com.zuipin.util.StaticConstants.MASTER;
import static com.zuipin.util.StaticConstants.STATIC;
import static com.zuipin.util.StaticConstants.SUFFIX;

public class FileUtil {

    private static Log log = LogFactory.getLog(FileUtil.class);

    public static File createFile(String path) throws IOException {
        StringBuilder sbf = new StringBuilder("/");
        String[] array = getFileDirArray(path);
        if (array.length < 2)
            return null;
        File file = null;
        for (int i = 0; i < array.length - 1; i++) {
            if (StringUtils.isBlank(array[i]))
                continue;
            sbf.append(array[i]);
            file = new File(sbf.toString());
            if (!file.exists()) {
                file.mkdir();
            }
            sbf.append(File.separator);
        }
        sbf.append(array[array.length - 1]);
        file = new File(sbf.toString());
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 将图片写入到磁盘
     * @param img 图片数据流
     * @param fileName 文件保存时的名称
     */
    public static void writeImageToDisk(byte[] img, String fileName, String filePath){
        try {
//            File file = new File(filePath + "\\" + fileName);
            File fileDir = new File(filePath);
            if(!fileDir .isDirectory()) {
                fileDir.mkdir();
            }
            File file = new File(filePath + "\\" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(img);
            fops.flush();
            fops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 根据地址获得数据的字节流
     * @param strUrl 网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl){
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 从输入流中获取数据
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    public static String getSystem() {
        return System.getProperty("os.name");
    }

    public static String[] getFileDirArray(String path) {
        String[] array = null;
        if (getSystem().equals("Linux")) {
            array = path.split(File.separator);
        } else {
            path = path.replace("/", "\\");
            array = path.split("\\\\");
        }
        return array;
    }

    public static boolean fileIsOperate(File file) {
        if (file.renameTo(file)) {
            return false;
        }
        return true;
    }

    public static boolean fileIsOperate(String path) {
        File file = new File(path);
        if (!fileIsOperate(file)) {
            return false;
        }
        return true;
    }

    public static String exists(String path) {
        File file = new File(ContextUtil.getRealPath() + path);
        if (file.exists())
            return path;
        return null;
    }

    public static boolean existsFile(String path) {
        File file = new File(ContextUtil.getRealPath() + path);
        return file.exists();
    }

    /**
     * freemark生成静态页面时，获取要生成的文件临时名称，将文件名加_temp，返回新文件名
     *
     * @param fileName
     * @return
     * @author:zhuanglt
     * @email:zlteng@xiangyu.cn
     * @创建日期:2012-8-7
     * @功能说明：
     */
    public static String newFileName(String fileName) {
        String newName = "";
        int idx = fileName.lastIndexOf(".");
        newName = new StringBuilder().append(fileName.substring(0, idx)).append("_tmp").append(SUFFIX).toString();
        return newName;
    }

    /**
     * freemark生成静态页面使用，生成临时文件后，传入文件路径，去掉文件中的_tmp， 将文件重命名，如果要重命名的文件已经存在先删除该文件
     *
     * @param newFilePath
     * @author:zhuanglt
     * @email:zlteng@xiangyu.cn
     * @创建日期:2012-8-7
     * @功能说明：
     */
    public static void rename(String newFilePath) {
        File file = new File(newFilePath);
        String newFileName = file.getName();
        System.out.println(newFileName);
        int idx = newFileName.indexOf(".");
        String originPath = newFileName.substring(0, idx - 4);
        File dest = new File(new StringBuilder().append(file.getParent()).append("/").append(originPath).append(SUFFIX).toString());
        if (dest.exists()) {
            dest.delete();
        }
        file.renameTo(dest);
        file.delete();
    }

    /**
     * @param htmlName 页面名称
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-8-8
     * @功能说明：读取单个html
     */
    public static String loadHTML(String htmlName) {
        try {
            String path = new StringBuilder().append(STATIC).append(MASTER).append(htmlName).append(SUFFIX).toString();
            if (existsFile(path)) {
                return path;
            } else {
                return new StringBuffer().append(STATIC).append(BACKUP).append(htmlName).append(SUFFIX).toString();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return new StringBuffer().append(STATIC).append(BACKUP).append(htmlName).append(SUFFIX).toString();
        }
    }

    /**
     * @param htmlName 文件夹名称
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-8-8
     * @功能说明：读取某个目录下的html
     */
    public static List<String> loadFolderHTML(String htmlName) {
        if (StringUtils.isBlank(htmlName))
            return null;
        int index = htmlName.lastIndexOf("/");
        String folderName = htmlName.substring(0, index);
        String folderPath = new StringBuilder().append(ContextUtil.getRealPath()).append(STATIC).append(MASTER).append(folderName).toString();
        File file = new File(folderPath);
        if (!file.exists()) {
            folderPath = new StringBuilder().append(ContextUtil.getRealPath()).append(STATIC).append(BACKUP).append(folderName).toString();
            file = new File(folderPath);
            if (!file.exists()) {
                return null;
            }
        }
        String[] htmlFile = file.list();
        if (htmlFile != null) {
            List<String> list = new ArrayList<String>();
            String path = "";
            for (String html : htmlFile) {
                file = new File(new StringBuilder().append(folderPath).append("/").append(html).toString());
                if (file.isDirectory())
                    continue;
                path = FileUtil.loadHTML(folderName + "/" + html.substring(0, html.indexOf(".")));
                if (StringUtils.isNotBlank(path)) {
                    list.add(path);
                }
            }
            return list;
        }
        return null;
    }

    public static List<String> channelFileSort(List<String> list) {
        if (StringUtils.isEmpty(list))
            return null;
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String f1, String f2) {
                f1 = f1.substring(f1.indexOf(".") - 1, f1.indexOf("."));
                f2 = f2.substring(f2.indexOf(".") - 1, f2.indexOf("."));
                return f1.compareTo(f2);
            }
        });
        return list;
    }
}
