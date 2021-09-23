package com.data.data.hmly.service.build.yhybuilder;

import com.data.data.hmly.service.build.YhyIndexService;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/1/12.
 */
@Service
public class YhyScenicBuilder {

    private Logger logger = Logger.getLogger(YhyScenicBuilder.class);

    private static final String YHY_SCENIC_INDEX_TEMPLATE = "/yhy/scenic/index.ftl";
    private static final String YHY_SCENIC_INDEX_TARGET = "/yhy/scenic/index.htm";

    @Resource
    private YhyAdsBuilder yhyAdsBuilder;
    @Resource
    private YhyIndexService yhyIndexService;


    public void buildYhyIndex() {
        Map<Object, Object> data = new HashMap<Object, Object>();
        // 广告
        List<Ads> adses = yhyAdsBuilder.getAds(YhyAdsBuilder.YHY_SCENIC_INDEX_TOP_BANNER);
        // 热门景点
        List<ScenicSolrEntity> hotScenics = yhyIndexService.getHotScenic();
        // 主题标签
        List<Label> themeLabels = yhyIndexService.getScenicIndexThemeLabel();
        // 主题推荐景点
        List<ScenicSolrEntity> themeScenics = yhyIndexService.getThemeRecScenic();
        data.put("adses", adses);
        data.put("hotScenics", hotScenics);
        data.put("themeLabels", themeLabels);
        data.put("themeScenics", themeScenics);
        logger.warn("一海游景点首页构建完成...!");
        FreemarkerUtil.create(data, YHY_SCENIC_INDEX_TEMPLATE, YHY_SCENIC_INDEX_TARGET);
    }
}
