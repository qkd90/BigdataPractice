package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author rq
 */
@Slf4j
@RestController
@RequestMapping("sms")

public class DemoController {

    private final RestTemplate restTemplate;

    static String mobile = "";

    public DemoController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @RequestMapping("send")
    public String sendSms(@RequestBody String requestInfo) {
        log.info("开始运行");
        log.debug("alert notify  params: {}", requestInfo);
        JSONObject json = JSON.parseObject(requestInfo);
        JSONObject commonAnnotations = json.getJSONObject("commonAnnotations");
        String content = commonAnnotations.getString("description");
        if ("".equals(mobile)) {
            mobile = readFileContent("phone.txt");
        }
        log.info("问题是：{}", content);
        String url = "http://112.35.1.155:1992/sms/norsubmit";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        log.info("正在发送");
        HttpEntity<String> request = new HttpEntity<>(getSmsParam(content,mobile), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        log.info("发送成功");
        String body = response.getBody();
        System.out.println("body" + body);
        return body;
    }

    @GetMapping("update")
    public String updateSms() {
        String mobiles = "";
        log.info("开始更新电话号码");
        log.debug("alert notify  params: {}",mobiles);
        mobiles = readFileContent("phone.txt");
        mobile = mobiles;
        return "修改成功";
    }

    private static String readFileContent(String filename) {
        log.info("目录地址："+System.getProperty("user.dir"));
        File file = new File(System.getProperty("user.dir"), filename);
        BufferedReader reader = null;
        StringBuilder sbf = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
                sbf.append(",");
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }

    public String getSmsParam(String content,String mobile) {
        Submit submit = new Submit();
        submit.setEcName("xxx");
        submit.setApId("xxx");
        submit.setSecretKey("xxx");
        submit.setMobiles(mobile);
        submit.setContent(content);
        submit.setSign("xxx");
        submit.setAddSerial("");

        //md5加密
        String sb = submit.getEcName() +
                submit.getApId() +
                submit.getSecretKey() +
                submit.getMobiles() +
                submit.getContent() +
                submit.getSign() +
                submit.getAddSerial();
        String selfMac = encryptToMD5(sb);
        System.out.println("selfMac:" + selfMac);
        submit.setMac(selfMac);

        //对象转json
        String param = JSON.toJSONString(submit);
        System.out.println("param:" + param);

        //base64加密
        String encode = Base64.encodeBase64String(param.getBytes());
        System.out.println(encode);

        return encode;
    }


    /**
     * md5 32位 小写
     *
     */
    public static String encryptToMD5(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                int number = b & 0xff;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    sb.append("0").append(hex);
                } else {
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}







