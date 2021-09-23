package com.zuipin.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

public class HTMLUtil {
    private final static Logger logger = Logger.getLogger(HTMLUtil.class);

    public static Boolean doPost(String uri, Map<String, String> params, String charset) {
        URL httpurl = null;
        HttpURLConnection httpConn = null;
        String line = "";
        try {
            httpurl = new URL(uri);
            httpConn = (HttpURLConnection) httpurl.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            PrintWriter outs = new PrintWriter(httpConn.getOutputStream());
            StringBuilder sb = new StringBuilder();
            if (!params.isEmpty()) {
                for (Entry<String, String> entry : params.entrySet()) {
                    if (sb.length() > 0) {
                        sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
                    } else {
                        sb.append(entry.getKey()).append("=").append(entry.getValue());
                    }
                }
            }
            logger.info("out:  " + sb.toString());
            outs.print(sb.toString());
            outs.flush();
            outs.close();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            while ((line = in.readLine()) != null) {
                logger.info(line);
            }
            in.close();

        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
        finally {
            if (httpConn != null)
                httpConn.disconnect();
        }
        return true;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("sim", "18950619888");
        if (doPost("http://218.6.59.90:9070/", params, "utf-8")) {
            System.out.println("success");
        } else {
            System.out.println("failure");
        }
    }
}
