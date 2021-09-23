package com.data.data.hmly.action.lvxbang.sitemap;

import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.request.HotelSearchRequest;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.request.PlanSearchRequest;
import com.data.data.hmly.service.plan.request.RecommendPlanSearchRequest;
import com.data.data.hmly.service.plan.vo.PlanSolrEntity;
import com.data.data.hmly.service.plan.vo.RecommendPlanSolrEntity;
import com.data.data.hmly.service.restaurant.DelicacyService;
import com.data.data.hmly.service.restaurant.request.DelicacySearchRequest;
import com.data.data.hmly.service.restaurant.vo.DelicacySolrEntity;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.request.ScenicSearchRequest;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import com.data.data.hmly.util.Clock;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;

/**
 * Created by huangpeijie on 2016-04-01,0001.
 */
public class SiteMap extends TimerTask {
    private final ScenicInfoService scenicInfoService;
    private final HotelService hotelService;
    private final DelicacyService delicacyService;
    private final PlanService planService;
    private final RecommendPlanService recommendPlanService;
    private final AreaService areaService;

    private String indexPath;
    private String destinationPath;
    private String planPath;
    private String mapPath;
    private String recommendPlanPath;
    private String scenicPath;
    private String trafficPath;
    private String hotelPath;
    private String delicacyPath;
    private final String siteMapPath;

    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private static final Logger LOGGER = Logger.getLogger(SiteMap.class);

    public SiteMap(ServletContext context) {
        ApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(context);
        scenicInfoService = (ScenicInfoService) appContext.getBean("scenicInfoService");
        hotelService = (HotelService) appContext.getBean("hotelService");
        delicacyService = (DelicacyService) appContext.getBean("delicacyService");
        planService = (PlanService) appContext.getBean("planService");
        recommendPlanService = (RecommendPlanService) appContext.getBean("recommendPlanService");
        areaService = (AreaService) appContext.getBean("areaService");
        siteMapPath = context.getRealPath("/");
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("config.properties");
        Properties p = new Properties();
        try {
            p.load(inputStream);
            indexPath = p.getProperty("INDEX_PATH");
            destinationPath = p.getProperty("DESTINATION_PATH");
            planPath = p.getProperty("PLAN_PATH");
            mapPath = p.getProperty("HANDDRAW_PATH");
            recommendPlanPath = p.getProperty("RECOMMENDPLAN_PATH");
            scenicPath = p.getProperty("SCENIC_PATH");
            trafficPath = p.getProperty("TRAFFIC_PATH");
            hotelPath = p.getProperty("HOTEL_PATH");
            delicacyPath = p.getProperty("DELICACY_PATH");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        createSiteMap();
    }

    public void createSiteMap() {
        Clock clock = new Clock();

        List<String> urlList = Lists.newArrayList();

        index(urlList);
        place(urlList);
        LOGGER.info("place cost: " + clock.elapseTime() + "ms");
        plan(urlList);
        LOGGER.info("plan cost: " + clock.elapseTime() + "ms");
        map(urlList);
        guide(urlList);
        LOGGER.info("guide cost: " + clock.elapseTime() + "ms");
        scenic(urlList);
        LOGGER.info("scenic cost: " + clock.elapseTime() + "ms");
        traffic(urlList);
        hotel(urlList);
        LOGGER.info("hotel cost: " + clock.elapseTime() + "ms");
        food(urlList);
        LOGGER.info("food cost: " + clock.elapseTime() + "ms");


        try {
            //生成sitemap.txt文件
            List<String> sitemapList = Lists.newArrayList();
            int i = urlList.size() / 50000;
            for (int j = 0; j <= i; j++) {
                int toIndex;
                if (j == i) {
                    toIndex = urlList.size();
                } else {
                    toIndex = (j + 1) * 50000;
                }
                List<String> list = urlList.subList(j * 50000, toIndex);
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(siteMapPath + "/sitemap" + (j + 1) + ".txt")));
                for (String s : list) {
                    if (s.equals(list.get(list.size() - 1))) {
                        writer.write(s);
                        break;
                    }
                    writer.write(s + "\n");
                }
                writer.close();
                sitemapList.add(indexPath + "/sitemap" + (j + 1) + ".txt");
            }

            //生成sitemap索引文件
            String last = format.format(new Date());
            Document document = DocumentHelper.createDocument();
            Element sitemapIndex = document.addElement("sitemapindex");
            for (String s : sitemapList) {
                Element sitemap = sitemapIndex.addElement("sitemap");
                Element loc = sitemap.addElement("loc");
                Element lastmod = sitemap.addElement("lastmod");

                loc.setText(s);
                lastmod.setText(last);
            }

            XMLWriter xmlWriter = new XMLWriter(new FileWriter(new File(siteMapPath + "/sitemapIndex.xml")));
            xmlWriter.write(document);
            xmlWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        formatXMLFile(siteMapPath + "/sitemapIndex.xml", "UTF-8");
        LOGGER.info("build sitemap.xml seccess, cose: " + clock.totalTime() + "ms");
    }

    //首页
    public void index(List<String> urlList) {
        urlList.add(indexPath);
    }

    //目的地
    public void place(List<String> urlList) {
        urlList.add(destinationPath);

        List<TbArea> areaList = areaService.listAllProvinceArea();
        for (TbArea area : areaList) {
            urlList.add(destinationPath + "/privince_" + area.getId() + ".html");
        }

        List<TbArea> cityList = areaService.listLevel2Area();
        for (TbArea area : cityList) {
            urlList.add(destinationPath + "/city_" + area.getId() + ".html");
        }
    }

    //私人定制
    public void plan(List<String> urlList) {
        urlList.add(planPath);
        urlList.add(planPath + "/plan_list.html");

        PlanSearchRequest request = new PlanSearchRequest();
        List<PlanSolrEntity> planList = planService.listFromSolr(request, new Page(0, Integer.MAX_VALUE));
        for (PlanSolrEntity plan : planList) {
            urlList.add(planPath + "/plan_detail_" + plan.getId() + ".html");
        }
    }

    //旅游地图
    public void map(List<String> urlList) {
        List<TbArea> areaList = areaService.getHandDrawCityList();
        for (TbArea area : areaList) {
            urlList.add(mapPath + "/map_" + area.getId() + ".html");
        }
    }

    //游记
    public void guide(List<String> urlList) {
        urlList.add(recommendPlanPath);
        urlList.add(recommendPlanPath + "/guide_list.html");

        RecommendPlanSearchRequest request = new RecommendPlanSearchRequest();
        List<RecommendPlanSolrEntity> recommendPlanList = recommendPlanService.listFromSolr(request, new Page(0, Integer.MAX_VALUE));
        for (RecommendPlanSolrEntity recommendPlan : recommendPlanList) {
            urlList.add(recommendPlanPath + "/guide_detail_" + recommendPlan.getId() + ".html");
        }
    }

    //景点
    public void scenic(List<String> urlList) {
        urlList.add(scenicPath);
        urlList.add(scenicPath + "/scenic_list.html");

        ScenicSearchRequest request = new ScenicSearchRequest();
        List<ScenicSolrEntity> scenicInfoList = scenicInfoService.listFromSolr(request, new Page(0, Integer.MAX_VALUE));
        for (ScenicSolrEntity scenic : scenicInfoList) {
            urlList.add(scenicPath + "/scenic_detail_" + scenic.getId() + ".html");
        }
    }

    //交通
    public void traffic(List<String> urlList) {
        urlList.add(trafficPath);
    }

    //酒店
    public void hotel(List<String> urlList) {
        urlList.add(hotelPath);
        urlList.add(hotelPath + "/hotel_list.html");

        HotelSearchRequest request = new HotelSearchRequest();
        List<HotelSolrEntity> hotelList = hotelService.listFromSolr(request, new Page(0, Integer.MAX_VALUE));
        for (HotelSolrEntity hotel : hotelList) {
            urlList.add(hotelPath + "/hotel_detail_" + hotel.getId() + ".html");
        }
    }

    //美食
    public void food(List<String> urlList) {
        urlList.add(delicacyPath);
        urlList.add(delicacyPath + "/food_list.html");

        DelicacySearchRequest request = new DelicacySearchRequest();
        List<DelicacySolrEntity> delicacyList = delicacyService.listFromSolr(request, new Page(0, Integer.MAX_VALUE));
        for (DelicacySolrEntity delicacy : delicacyList) {
            urlList.add(delicacyPath + "/food_detail_" + delicacy.getId() + ".html");
        }
    }

    /**
     * 格式化XML文档,并解决中文问题
     * @param xmlpath：xml文件路径
     * @param charSet:格式化的字符集
     * @return
     */
    public static boolean formatXMLFile(String xmlpath, String charSet) {

        boolean returnValue = false;
        try {

            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(new File(xmlpath));
            XMLWriter output = null;
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding(charSet);
            output = new XMLWriter(new FileWriter(new File(xmlpath)), format);
            output.write(document);
            output.close();
            document = null;
            saxReader = null;
            returnValue = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return returnValue;
    }
}
