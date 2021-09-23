package com.data.data.hmly.service.build.yhybuilder;

import com.data.data.hmly.service.build.enums.BuilderStatus;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.entity.enums.ScenicInfoType;
import com.data.data.hmly.service.ticket.TicketExplainService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketExplain;
import com.data.data.hmly.service.ticket.entity.enmus.TicketType;
import com.data.data.hmly.util.Clock;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.ConcurrentUtil;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.SpringContextHolder;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by dy on 2016/12/28.
 */
@Service
public class YhySailboatBuilder {


    private Logger logger = Logger.getLogger(YhySailboatBuilder.class);

    private static final String YHY_SAILBOAT_INDEX_TEMPLATE = "/yhy/sailboat/index.ftl";
    private static final String YHY_SAILBOAT_INDEX_TARGET = "/yhy/sailboat/index.htm";
    private static final String YHY_SAILBOAT_DETAIL_TEMPLATE = "/yhy/sailboat/detail.ftl";
    private static final String YHY_SAILBOAT_DETAIL_TARGET = "/yhy/sailboat/detail%d.htm";
    private static final String YHY_SAILBOAT_HEAD_TEMPLATE = "/yhy/sailboat/head.ftl";
    private static final String YHY_SAILBOAT_HEAD_TARGET = "/yhy/sailboat/head%d.htm";

    private final AtomicInteger buildingCount = new AtomicInteger();
    private final AtomicLong buildingCost = new AtomicLong();
    private static Long currentId;
    private BuilderStatus status = BuilderStatus.IDLE;
    private static final int PAGE_SIZE = 100;

    @Resource
    private YhyAdsBuilder yhyAdsBuilder;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private TicketService ticketService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private TicketExplainService ticketExplainService;
    @Resource
    private CommentService commentService;

    public void buildYhySailboatIndex() {
        Map<Object, Object> data = new HashMap<Object, Object>();
        //广告
        List<Ads> adses = yhyAdsBuilder.getAds(YhyAdsBuilder.YHY_SAILBOAT_INDEX_TOP_BANNER);

        //登船地点
        ScenicInfo scenicInfo = new ScenicInfo();
        scenicInfo.setScenicType(ScenicInfoType.sailboat);
        TbArea tbArea = new TbArea();
        tbArea.setId(3502L);
        scenicInfo.setCity(tbArea);
        scenicInfo.setStatus(1);
        List<ScenicInfo> scenicInfos = scenicInfoService.list(scenicInfo, null);


        //热门推荐
        Ticket ticket = new Ticket();
        List<TicketType> includeTicketTypeList = Lists.newArrayList();
        includeTicketTypeList.add(TicketType.sailboat);
        includeTicketTypeList.add(TicketType.yacht);
        includeTicketTypeList.add(TicketType.huanguyou);
        ticket.setIncludeTicketTypeList(includeTicketTypeList);
        ticket.setStatus(ProductStatus.UP);
        Page page = new Page(1, 5);
        List<Product> sailboatList = ticketService.findListBySql(ticket, page);

        data.put("adses", adses);
        data.put("scenicInfos", scenicInfos);
        data.put("sailboatList", sailboatList);
        FreemarkerUtil.create(data, YHY_SAILBOAT_INDEX_TEMPLATE, YHY_SAILBOAT_INDEX_TARGET);
        logger.info("一海游游艇帆船首页构建完成...!");
    }

    public void buildSailboatDetail(Long id) {
        Ticket ticket = ticketService.loadTicket(id);
        buildYhyDetail(ticket);
    }

    private void buildYhyDetail(Ticket ticket) {
        if (ticket.getStatus() != ProductStatus.UP) {
            return;
        }
        Clock clock = new Clock();
        Map<Object, Object> data = Maps.newHashMap();
        List<Productimage> productimages = productimageService.findImagesByProductId(ticket.getId());   //获取产品所有图片
        TicketExplain explain = ticketExplainService.findExplainByTicketId(ticket.getId());     //获取ticketExplain
        data.put("ticket", ticket);
        data.put("productimages", productimages);
        data.put("explain", explain);
        FreemarkerUtil.create(data, YHY_SAILBOAT_HEAD_TEMPLATE, String.format(YHY_SAILBOAT_HEAD_TARGET, ticket.getId()));
        FreemarkerUtil.create(data, YHY_SAILBOAT_DETAIL_TEMPLATE, String.format(YHY_SAILBOAT_DETAIL_TARGET, ticket.getId()));
        currentId = ticket.getId();
        buildingCount.getAndIncrement();
        buildingCost.getAndAdd(clock.totalTime());
        logger.info("build sailboat detail#" + ticket.getId() + " success, cost " + clock.totalTime());
    }

    public void buildYhyDetail() {
        if (status == BuilderStatus.RUNNING) {
            return;
        }
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return buildYhyDetailTask();
            }
        });
    }

    public void buildYhyDetail(final Long startId, final Long endId) {
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return buildYhyDetailTask(startId, endId);
            }
        });
    }

    private Object buildYhyDetailTask() {
        status = BuilderStatus.RUNNING;
        int current;
        int page = 1;
        do {
            SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
            List<TicketType> ticketTypes = Lists.newArrayList();
            ticketTypes.add(TicketType.sailboat);
            ticketTypes.add(TicketType.yacht);
            Ticket queryTicket = new Ticket();
            queryTicket.setIncludeTicketTypeList(ticketTypes);
            List<Ticket> ticketList = ticketService.findTicketList(queryTicket, new Page(page, PAGE_SIZE));
            for (Ticket ticket : ticketList) {
                buildYhyDetail(ticket);
            }
            current = ticketList.size();
            page++;
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);

        } while (current == PAGE_SIZE);
        status = BuilderStatus.IDLE;
        return null;
    }

    private Object buildYhyDetailTask(Long startId, Long endId) {
        status = BuilderStatus.RUNNING;
        buildingCount.set(0);
        buildingCost.set(0);
        currentId = startId - 1;
        int current;
        Clock clock = new Clock();
        do {
            SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
            List<TicketType> ticketTypes = Lists.newArrayList();
            ticketTypes.add(TicketType.sailboat);
            ticketTypes.add(TicketType.yacht);
            List<Ticket> ticketList = ticketService.getInIdRange(currentId, endId, ticketTypes, PAGE_SIZE);
            for (Ticket ticket : ticketList) {
                buildYhyDetail(ticket);
                currentId = ticket.getId();
                logger.info("build #" + ticket.getId() + "finished, cost " + clock.elapseTime());
            }
            current = ticketList.size();
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        } while (current == PAGE_SIZE);
        logger.info("build finished, cost " + clock.totalTime() + "ms");
        status = BuilderStatus.IDLE;
        return null;
    }
}
