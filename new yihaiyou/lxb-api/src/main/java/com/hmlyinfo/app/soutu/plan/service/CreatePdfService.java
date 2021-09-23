package com.hmlyinfo.app.soutu.plan.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.app.soutu.plan.domain.Plan;

@Service
public class CreatePdfService {
    
    @Autowired
    private PlanService planService;
    
    private static final String WK_PDF_COMMAND = Config.get("WK_HTML_TO_PDF");
    private static final String PDF_ADDRESS = Config.get("PDF_ADDRESS");
    
    Logger logger = Logger.getLogger(CreatePdfService.class);
    
    
    // 根据传输参数把网页转化为pdf文件，返回完整pdf文件路径
    public Map<String, Object> createPdf(String url, long planId) throws Exception{
        
        Plan plan = planService.info(planId);
        String fileName = planId + "_" + plan.getPlanName() + ".pdf";
        // 生成文件的完整路径
        String filePath = PDF_ADDRESS + fileName;
        InputStreamReader ir = null;
        try {
            // wkhtmltopdf command
            // $wkhtmltopdf www.lvxbang.com ./lvxbang.pdf
            String commands = WK_PDF_COMMAND + " " + url + " " + filePath;

            Process process = Runtime.getRuntime().exec(commands);

            // for showing the info on screen
            ir = new InputStreamReader(process.getInputStream());

            BufferedReader input = new BufferedReader(ir);

            String line;

            // 在Windows下while循环未知问题
            while ((line = input.readLine()) != null) {
                logger.info(line);
            }
            process.destroy();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (ir != null) {
                ir.close();
            }
        }
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("filePath", filePath);
        resMap.put("fileName", fileName);
        return resMap;
    }
}
