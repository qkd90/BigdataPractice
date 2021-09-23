package com.yihaiyou.mobile;

import com.zuipin.util.FileUtils;
import com.zuipin.util.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 部署工具类，主要解决发布包前端文件版本、压缩、优化
 * 以第三方脚本不加版本号为原则，尽量达到浏览器缓存（视具体浏览器而定）
 * Created by caiys on 2017/1/14.
 */
public class DeployUtil {
    private static Log log = LogFactory.getLog(DeployUtil.class);
    private static final String PROJECT_NAME = "yihaiyouweb";
    private static final String TARGET_PROJECT_NAME = PROJECT_NAME + "-0.0.1-SNAPSHOT";
    private static final String MAIN_FILE_NAME = "yihaiyou.html";
    private static final String DEPLOY_TEMP_FILE_NAME = "yihaiyou_deploy.html";
    private static String WORKSPACE_DIR = null;
    private static final String DEPLOY_NAME = "m-webapp";

    public static void main(String[] args) throws IOException, InterruptedException {
        // 设置工作路径
        String currentUPath = DeployUtil.class.getClass().getResource("/").getPath();
        int pathIndex = currentUPath.indexOf(PROJECT_NAME) + PROJECT_NAME.length() + 1;
        WORKSPACE_DIR = currentUPath.substring(0, pathIndex) + "target/";
        log.info("current workspace: " + WORKSPACE_DIR);

        String dir = WORKSPACE_DIR + TARGET_PROJECT_NAME + "/";
        String deployDir = WORKSPACE_DIR + DEPLOY_NAME + "/";
        // 删除旧文件
        File oldDeployDir = new File(deployDir);
        if (oldDeployDir.exists()) {
            FileUtils.deleteDir(oldDeployDir);
        }
        // 复制主文件
        copyMainHtml(dir);
        log.info("copy main file success. ");

        // 执行cmd命令
        /*
        * 使用fis工具，参数说明，视情况而加：-o压缩优化，-p资源合并（代码不规范容易出问题），-m带版本号，-d输出目录，--verbose输出详细信息。
        */
        String cmd = "cmd /k cd " + dir.substring(1) + " && fis release -opmd ../" + DEPLOY_NAME + " && exit";
        Runtime run = Runtime.getRuntime();
        Process process = run.exec(cmd);
        InputStream input = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String szline = null;
        while ((szline = reader.readLine()) != null) {
            log.info(szline);
        }
        reader.close();
        process.waitFor();
        process.destroy();
        log.info("fis deploy file success. ");

        // 恢复主html文件，并复制缺失的第三方依赖包
        restoreMainHtml(dir);
        log.info("restore main file and copy libs success. ");

        // 执行批处理，文件替换和覆盖
        cmd = deployDir.substring(1) + "m.bat";
        run = Runtime.getRuntime();
        process = run.exec(cmd, null, oldDeployDir);
        input = process.getInputStream();
        reader = new BufferedReader(new InputStreamReader(input));
        szline = null;
        while ((szline = reader.readLine()) != null) {
            log.info(szline);
        }
        reader.close();
        process.waitFor();
        process.destroy();
        log.info("finish deploy file success. ");
    }

    /**
     * 恢复主html文件，并复制缺失的第三方依赖包
     */
    private static void restoreMainHtml(String dir) throws IOException {
        String deployDir = WORKSPACE_DIR + DEPLOY_NAME + "/";
        BufferedReader in = new BufferedReader(new FileReader(deployDir + DEPLOY_TEMP_FILE_NAME));
        BufferedWriter write = new BufferedWriter(new FileWriter(deployDir + MAIN_FILE_NAME));
        String line = null;
        Pattern p = Pattern.compile("= *('|\")libs/");
        while ((line = in.readLine()) != null) {
            if (StringUtils.isBlank(line)) {
                write.write(line);
                write.newLine();
                continue;
            }
            Matcher m = p.matcher(line);
            if (m.find() && line.indexOf("<!--##") > -1 && line.indexOf("###-->") > -1) {
                write.write(line.substring("<!--##".length(), line.length() - "###-->".length())); // 去掉注释
            } else {
                write.write(line);
            }
            write.newLine();
        }
        write.flush();
        write.close();
        in.close();
        // 删除临时文件
        File file = new File(deployDir + DEPLOY_TEMP_FILE_NAME);
        file.delete();

        // 复制缺失的第三方依赖包
        File srcFile = new File(dir + "libs");
        File destFile = new File(deployDir + "libs");
        copyFolder(srcFile, destFile);
    }

    /**
     * 复制一个目录及其子目录、文件到另外一个目录
     * @param src
     * @param dest
     * @throws IOException
     */
    private static void copyFolder(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdir();
            }
            String files[] = src.list();
            for (String file : files) {
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                // 递归复制
                copyFolder(srcFile, destFile);
            }
        } else {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
        }
    }

    /**
     * 复制主html文件，并注释相关第三方依赖包
     */
    private static void copyMainHtml(String dir) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(dir + MAIN_FILE_NAME));
        BufferedWriter write = new BufferedWriter(new FileWriter(dir + DEPLOY_TEMP_FILE_NAME));
        String line = null;
        Pattern p = Pattern.compile("= *('|\")libs/");
        while ((line = in.readLine()) != null) {
            if (StringUtils.isBlank(line)) {
                write.write(line);
                write.newLine();
                continue;
            }
            Matcher m = p.matcher(line);
            if (m.find() && line.indexOf("<!--") < 0 && line.indexOf("-->") < 0) {  // 排除原来已注释行
                write.write("<!--##" + line + "###-->"); // 加上注释
            } else {
                write.write(line);
            }
            write.newLine();
        }
        write.flush();
        write.close();
        in.close();

        // 删除主文件，不然会影响打包
//        File file = new File(dir + MAIN_FILE_NAME);
//        file.delete();
    }

}
