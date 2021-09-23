package com.yihaiyou.pc;

import com.zuipin.util.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 部署工具类，主要解决发布包前端文件版本、压缩、优化
 *
 * Created by caiys on 2017/2/13.
 */
public class DeployUtil {
    private static Log log = LogFactory.getLog(DeployUtil.class);
    private static final String PROJECT_NAME = "yhypcweb";
    private static final String TARGET_PROJECT_NAME = PROJECT_NAME + "-0.0.1-SNAPSHOT";
    private static String WORKSPACE_DIR = null;
    private static final String DEPLOY_NAME = "web-webapp";

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
        // 执行cmd命令
        /*
        * 使用fis工具，参数说明，视情况而加：-o压缩优化，-p资源合并（代码不规范容易出问题），-m带版本号，-d输出目录，--verbose输出详细信息。
        */
        String cmd = "cmd /k cd " + dir.substring(1) + " && fis release -md ../" + DEPLOY_NAME + " && exit";
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

        // 执行批处理，文件替换和覆盖
        cmd = deployDir.substring(1) + "web.bat";
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


}
