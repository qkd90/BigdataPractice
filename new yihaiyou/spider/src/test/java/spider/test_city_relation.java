package spider;

import com.data.spider.service.mfw.MfwService;
import com.data.spider.service.pojo.mfw.MddSearchResult;
import com.data.spider.service.pojo.tb.AreaRelation;
import com.data.spider.service.tb.AreaRelationService;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.SpringContextHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sane on 15/9/8.
 */
public class test_city_relation {

    public static void main(String[] args) {
        getMfwNoteCity();
    }

    private static ApplicationContext ac;


    /**
     * 马蜂窝对应id抓取，tb_area_relation表
     */
    public static void getMfwNoteCity() {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        AreaRelationService areaRelationService = SpringContextHolder.getBean("areaRelationService");
        Criteria<AreaRelation> c = new Criteria<AreaRelation>(AreaRelation.class);
//        c.eq("name","大同市");
        c.isNull("mfwNoteCity");
        List<AreaRelation> citys = areaRelationService.gets(6000, c);
        System.out.println("名称\t区域\t匹配类型\t匹配名称\t匹配区域\turl");
        MfwService mfwService = new MfwService();
        for (AreaRelation city : citys) {
            if (city.getId() % 100 != 0)
                continue;
//            String keyword = city.getName().replace("市","");
            String keyword = city.getName().substring(0, 3);
            ////按关键字获得搜索结果
            MddSearchResult searchResult = mfwService.searchMdd(keyword.trim());
            //交通枢纽类型变量
            boolean matched = false;
            String id = "";
            ////交通枢纽类型匹配
            for (MddSearchResult.DataEntity.ListEntity data : searchResult.getData().getList()) {
                if (data.getSubname().contains("目的地")) {
                    System.out.println(keyword + "\t"
                            + data.getSubname() + "\t" + data.getName() + "\t" + "\t" + data.getUrl());
                    if (data.getName().contains(keyword.replace("市", ""))) {
                        matched = true;
                        Pattern p = Pattern.compile("&id=(\\d+)");
                        Matcher m = p.matcher(data.getUrl());
                        if (m.find()) {
                            id = m.group(1);
                        }
                        break;
                    }
                }
            }
            if (matched) {
                city.setMfwNoteCity(Integer.parseInt(id));
                areaRelationService.update(city);
            } else {
                System.err.println(keyword + "\t" + "未匹配");
            }

        }
    }


}
