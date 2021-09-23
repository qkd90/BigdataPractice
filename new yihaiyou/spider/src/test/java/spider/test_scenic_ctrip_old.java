package spider;

import com.data.spider.service.ctrip.CtripService;
import com.data.spider.service.data.DataCityService;
import com.data.spider.service.data.DataScenicRelationService;
import com.data.spider.service.data.ScenicService;
import com.data.spider.service.pojo.Scenic;
import com.data.spider.service.pojo.ctrip.Image;
import com.data.spider.service.pojo.ctrip.ScenicDetail;
import com.data.spider.service.pojo.ctrip.ScenicList;
import com.data.spider.service.pojo.data.DataCity;
import com.data.spider.service.pojo.tb.DataScenicRelation;
import com.data.spider.service.pojo.tb.TbScenicInfo;
import com.data.spider.service.pojo.tb.TbScenicOther;
import com.data.spider.service.tb.TbScenicInfoService;
import com.data.spider.service.tb.TbScenicOtherService;
import com.data.spider.util.QiniuUtil;
import com.framework.hibernate.util.Criteria;
import com.google.gson.Gson;
import com.zuipin.util.SpringContextHolder;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sane on 15/9/8.
 */
public class test_scenic_ctrip_old {

    private static ApplicationContext ac;

    public static void main(String[] args) {
//        crawlComment();
//        crawlCover();
        crawlGallery();
//        crawlGuide();
//        crawlIntroduction();
//        updateAddress();
//        updateOrderNum();
//        updateCityOrderNum();
//        updateCover();
    }

    //携程景点交通指南抓取
    public static void crawlGuide() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        Criteria<TbScenicInfo> c = new Criteria<TbScenicInfo>(TbScenicInfo.class);
//        c.isNull("guide");
        c.eq("guide", "");
        c.lt("id", 1000000L);
        List<TbScenicInfo> scenicInfos = tbScenicInfoService.gets(3000, c);
        for (TbScenicInfo scenicInfo : scenicInfos) {
            Criteria<Scenic> c1 = new Criteria<Scenic>(Scenic.class);
            c1.eq("id", scenicInfo.getId());
            String ctripId = scenicInfo.getCtripId().toString();
            System.err.println("ctripId= " + ctripId);
            String traffic = CtripService.getScenicTraffic(ctripId);
            System.err.println(traffic);
            if (traffic == null)
                continue;
            Pattern p_TagA = Pattern.compile("<a.*?>", Pattern.CASE_INSENSITIVE);
            Matcher m_html = p_TagA.matcher(traffic);
            traffic = m_html.replaceAll(""); // 过滤html标签
            traffic = traffic.replace("</a>", "");
            System.err.println(scenicInfo.getId() + "\t:" + ctripId);
            System.out.println(traffic);
//            scenicInfo.setGuide(intro);
//            tbScenicInfoService.update(scenicInfo);
        }
    }

    //携程地址核对
    public static void updateAddress() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        Criteria<TbScenicInfo> c = new Criteria<TbScenicInfo>(TbScenicInfo.class);
        c.isNull("updatedBy");
        c.isNull("createdBy");
        c.isNull("userId");
        c.gt("id", 10000L);
        c.isNotNull("ctripId");
//        c.eq("coverLarge", "");
        List<TbScenicInfo> scenicInfos = tbScenicInfoService.gets(1000000, c);
        for (TbScenicInfo scenicInfo : scenicInfos) {
            System.out.println(scenicInfo.getId() + "\t" + scenicInfo.getName() + "\t" + scenicInfo.getAddress());
            Criteria<Scenic> c1 = new Criteria<Scenic>(Scenic.class);
            c1.eq("id", scenicInfo.getId());
            String ctripId = scenicInfo.getCtripId().toString();
            String addr = null;
            ScenicDetail resultEntity = CtripService.getScenicDetail(ctripId);
            if (resultEntity != null && resultEntity.getSightDetailAggregate() != null && resultEntity.getSightDetailAggregate().getImageCoverUrl() != null) {
                addr = resultEntity.getSightDetailAggregate().getAddress();
            }
            if (addr == null || scenicInfo.getAddress().equals(addr))
                continue;
            System.err.println(scenicInfo.getId() + "\t" + scenicInfo.getName() + "\t" + scenicInfo.getAddress() + "\t" + addr);
            scenicInfo.setAddress(addr);
            tbScenicInfoService.update(scenicInfo);
        }
    }

    //携程排名核对
    public static void updateOrderNum() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        Criteria<TbScenicInfo> c = new Criteria<TbScenicInfo>(TbScenicInfo.class);
//        c.isNull("updatedBy");
//        c.isNull("createdBy");
//        c.isNull("userId");
//        c.lt("id", 10000L);
//        c.gt("id", 7409L);
        c.like("cityCode", "1307%");
        c.isNotNull("ctripId");
//        c.eq("coverLarge", "");
        List<TbScenicInfo> scenicInfos = tbScenicInfoService.gets(1000000, c);
        for (TbScenicInfo scenicInfo : scenicInfos) {
            System.out.println(scenicInfo.getId() + "\t" + scenicInfo.getName() + "\t" + scenicInfo.getAddress());
            Criteria<Scenic> c1 = new Criteria<Scenic>(Scenic.class);
            c1.eq("id", scenicInfo.getId());
            String ctripId = scenicInfo.getCtripId().toString();
            int orderNum = 0;
            ScenicDetail resultEntity = CtripService.getScenicDetail(ctripId);
            if (resultEntity != null && resultEntity.getSightDetailAggregate() != null) {
                orderNum = resultEntity.getSightDetailAggregate().getRank();
            }

            System.err.println(scenicInfo.getId() + "\t" + scenicInfo.getName() + "\t" + scenicInfo.getOrderNum() + "\t" + orderNum);
            scenicInfo.setOrderNum(orderNum);
            tbScenicInfoService.update(scenicInfo);
        }
    }

    //携程排名核对
    public static void updateCityOrderNum() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        DataCityService cityService = SpringContextHolder.getBean("dataCityService");
        Criteria<DataCity> c = new Criteria<DataCity>(DataCity.class);
//        int cityId = 130700;
//        c.eq("id", cityId);
        List<DataCity> citys = cityService.gets(500, c);
        CtripService ctripService = new CtripService();
        for (DataCity city : citys) {
            System.out.println(city.getCityName());
            int ctripCity = city.getCtripCityId();
            int sum = 1;
            List<ScenicList.SightListEntity> list = ctripService.getScenicAll(ctripCity);
            for (ScenicList.SightListEntity result : list) {
                int sightId = result.getSightId();
//                System.out.println(sightId + "\t" + sum++);
                System.out.println("UPDATE tb_scenic_info set order_num = " + sum++
                        + " WHERE ctrip_id = " + sightId + " ;");
            }
        }
    }

    //携程景点封面抓取
    public static void crawlCover() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        Criteria<TbScenicInfo> c = new Criteria<TbScenicInfo>(TbScenicInfo.class);
        c.isNull("coverLarge");
        c.isNotNull("ctripId");
//        c.eq("coverLarge", "");
        List<TbScenicInfo> scenicInfos = tbScenicInfoService.gets(1000000, c);
        for (TbScenicInfo scenicInfo : scenicInfos) {
            Criteria<Scenic> c1 = new Criteria<Scenic>(Scenic.class);
            c1.eq("id", scenicInfo.getId());
            String ctripId = scenicInfo.getCtripId().toString();
            System.err.println("ctripId= " + ctripId);
            System.err.println("--------------------");
            String cover = null;
            ScenicDetail resultEntity = CtripService.getScenicDetail(ctripId);
            if (resultEntity != null && resultEntity.getSightDetailAggregate() != null && resultEntity.getSightDetailAggregate().getImageCoverUrl() != null) {
                cover = resultEntity.getSightDetailAggregate().getImageCoverUrl().replace("_C_200_200", "");
            }
            if (cover == null)
                continue;
            String covery_large = QiniuUtil.UploadToQiniu(cover, "gallery/" + scenicInfo.getId());
            scenicInfo.setCoverLarge(covery_large);
            tbScenicInfoService.update(scenicInfo);
            System.err.println(scenicInfo.getId() + "\t:" + ctripId);
            System.out.println(cover);
        }
    }


    //携程景点封面上传
    public static void updateCover() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        ScenicService scenicService = SpringContextHolder.getBean("scenicService");
        Criteria<TbScenicInfo> c = new Criteria<TbScenicInfo>(TbScenicInfo.class);
        c.like("coverLarge", "%ctrip.com%");
        List<TbScenicInfo> scenicInfos = tbScenicInfoService.gets(1000000, c);
        for (TbScenicInfo scenicInfo : scenicInfos) {
            String cover = scenicInfo.getCoverLarge().replace("_C_200_200", "");
            if (cover == null)
                continue;
            String covery_large = QiniuUtil.UploadToQiniu(cover, "gallery/" + scenicInfo.getId());
            scenicInfo.setCoverLarge(covery_large);
            tbScenicInfoService.update(scenicInfo);
            System.out.println(cover);
            System.out.println(covery_large);
        }
    }

    //携程景点相册
    public static void crawlGallery() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        Criteria<TbScenicInfo> c = new Criteria<TbScenicInfo>(TbScenicInfo.class);
        c.eq("id", 1051644L);
//        c.like("coverLarge", "%ctrip.com%");
        List<TbScenicInfo> scenicInfos = tbScenicInfoService.gets(1, c);
        int imageStart = 0;
        for (TbScenicInfo scenicInfo : scenicInfos) {
//            List<Image> images = CtripService.getCtripImagesById(scenicInfo.getCtripId().toString(), String.valueOf(imageStart));
            List<Image> images = CtripService.getCtripImagesById(scenicInfo.getCtripId().toString(), String.valueOf(imageStart));
            System.out.println(new Gson().toJson(images));

            for (Image image : images) {
                System.out.println(image.SourceUrl);
            }

        }
    }


    //携程一句话点评抓取
    public static void crawlComment() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        ScenicService scenicService = SpringContextHolder.getBean("scenicService");
        DataScenicRelationService dataScenicRelationService = SpringContextHolder.getBean("dataScenicRelationService");
        Criteria<TbScenicInfo> c = new Criteria<TbScenicInfo>(TbScenicInfo.class);
        c.like("shortComment", "%来自\n%");
        List<TbScenicInfo> scenicInfos = tbScenicInfoService.gets(1000000, c);
        for (TbScenicInfo scenicInfo : scenicInfos) {
            Criteria<DataScenicRelation> c1 = new Criteria<DataScenicRelation>(DataScenicRelation.class);
            c1.eq("scenicId", scenicInfo.getId());
            c1.eq("source", "ctrip");
            Criteria<Scenic> c2 = new Criteria<Scenic>(Scenic.class);
            c2.eq("id", scenicInfo.getId());
            String ctripId = scenicInfo.getCtripId().toString();
            String shortComment = null;
//            ResultEntity resultEntity = CtripService.getScenicDetail(ctripId).getResult();
//            //限制一句话评论小于500字
//            if(resultEntity != null && resultEntity.getCommentDetailList().size() > 0){
//            	for(CommentDetailListEntity commentDetailListEntity:resultEntity.getCommentDetailList()){
//            		if(commentDetailListEntity.getCommentContent().length() < 500){
//            			shortComment = commentDetailListEntity.getCommentContent();
//            		}else {
//            			continue;
//            		}
//            	}
//            }
            if (shortComment == null)
                continue;
            scenicInfo.setShortComment(shortComment);
            System.err.println("景点名: " + scenicInfo.getName() + "评论:" + scenicInfo.getShortComment());
//            tbScenicInfoService.update(scenicInfo);
            System.err.println(scenicInfo.getId() + "\t:" + ctripId);
            System.out.println(shortComment);
        }
    }


    //携程景点简介抓取
    public static void crawlIntroduction() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        TbScenicOtherService tbScenicOtherService = SpringContextHolder.getBean("tbScenicOtherService");
        TbScenicInfoService tbScenicInfoService = SpringContextHolder.getBean("tbScenicInfoService");
        Criteria<TbScenicOther> c = new Criteria<TbScenicOther>(TbScenicOther.class);
        c.gt("scenicInfoId", 1050518L);
//        c.like("cityCode", "11%");
        c.isNull("introduction");

        ProjectionList proList = Projections.projectionList();//设置投影集合
        proList.add(Projections.groupProperty("scenicInfoId"));
        c.setProjection(proList);
//        criteria.SetProjection(proList).SetResultTransformer(new NHibernate.Transform.AliasToBeanResultTransformer(typeof(AchievementSummary))); // 要
        List<TbScenicOther> scenicOthers = tbScenicOtherService.gets(10000, c);
        for (TbScenicOther scenicOther : scenicOthers) {
            System.out.println(new Gson().toJson(scenicOther));
            Criteria<TbScenicInfo> c1 = new Criteria<TbScenicInfo>(TbScenicInfo.class);
            c1.eq("id", scenicOther.getScenicInfoId());
            TbScenicInfo scenicInfo = tbScenicInfoService.gets(1, c1).get(0);
            String ctripId = scenicInfo.getCtripId().toString();
            System.out.println(ctripId);
            String intro = null;
            ScenicDetail resultEntity = CtripService.getScenicDetail(ctripId);
            if (resultEntity != null) {
                intro = resultEntity.getSightDetailAggregate().getIntroduction();
            }
            if (intro == null)
                continue;
            Pattern p_TagA = Pattern.compile("<a.*?>", Pattern.CASE_INSENSITIVE);
            Matcher m_html = p_TagA.matcher(intro);
            intro = m_html.replaceAll(""); // 过滤html标签
            intro = intro.replace("</a>", "");
            System.err.println(scenicOther.getScenicInfoId() + "\t:" + ctripId);
            System.out.println(intro);
            scenicOther.setIntroduction(intro);
            tbScenicOtherService.update(scenicOther);
        }
    }
}
