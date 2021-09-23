package com.data.data.hmly.service.elong.util;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Http {

    public static String HttpsSend(String method, String url, String data) {
//        System.setProperty("http.proxyHost", "localhost");
//        System.setProperty("http.proxyPort", "8888");
//        System.setProperty("https.proxyHost", "localhost");
//        System.setProperty("https.proxyPort", "8888");
        try {
            HttpsNotVerifyUtil.trustAllHttpsCertificates();
            HttpsURLConnection.setDefaultHostnameVerifier(HttpsNotVerifyUtil.hv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpsURLConnection conn = null;
        InputStream in = null;
        InputStreamReader isr = null;
        OutputStream out = null;
        StringBuffer result = null;
        try {
            URL u = new URL(url);
//            conn = (HttpsURLConnection) u.openConnection();
            conn = (HttpsURLConnection) u.openConnection();
            conn.setRequestProperty("Accept-Encoding", "gzip");
            //conn.setRequestProperty("Content-Type", "");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);

            if (method.equals("POST")) {
                byte[] sendbyte = data.getBytes("UTF-8");
                out = conn.getOutputStream();
                out.write(sendbyte);
            }
            int status = conn.getResponseCode();
            if (status == 200) {
                String enc = conn.getContentEncoding();
                result = new StringBuffer();
                in = conn.getInputStream();
                enc = conn.getContentEncoding();
                if (enc != null && enc.equals("gzip")) {
                    java.util.zip.GZIPInputStream gzin = new java.util.zip.GZIPInputStream(in);
                    isr = new InputStreamReader(gzin, "UTF-8");
                } else {
                    isr = new InputStreamReader(in, "UTF-8");
                }
                char[] c = new char[1024];
                int a = isr.read(c);
                while (a != -1) {
                    String cha = new String(c, 0, a);
                    result.append(cha);
                    a = isr.read(c);
                }
            } else {
                System.out.println("http code = " + status);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            try {
                if (in != null) {
                    in.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result == null ? null : result + "";
    }

    public static String HttpSend(String method, String url, String data) {

        HttpURLConnection conn = null;
        InputStream in = null;
        InputStreamReader isr = null;
        OutputStream out = null;
        StringBuffer result = null;
        try {
            URL u = new URL(url);
            conn = (HttpURLConnection) u.openConnection();
            conn.setRequestProperty("Accept-Encoding", "gzip");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);

            if (method.equals("POST")) {
                byte[] sendbyte = data.getBytes("UTF-8");
                out = conn.getOutputStream();
                out.write(sendbyte);
            }
            int status = conn.getResponseCode();
            if (status == 200) {
                String enc = conn.getContentEncoding();
                result = new StringBuffer();
                in = conn.getInputStream();
                enc = conn.getContentEncoding();
                if (enc != null && enc.equals("gzip")) {
                    java.util.zip.GZIPInputStream gzin = new java.util.zip.GZIPInputStream(in);
                    isr = new InputStreamReader(gzin, "UTF-8");
                } else {
                    isr = new InputStreamReader(in, "UTF-8");
                }
                char[] c = new char[1024];
                int a = isr.read(c);
                while (a != -1) {
                    String cha = new String(c, 0, a);
                    result.append(cha);
                    a = isr.read(c);
                }
            } else {
                System.out.println("http code = " + status);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            try {
                if (in != null) {
                    in.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result == null ? null : result + "";
    }
}
