package com.data.data.hmly.service.nctripticket;

import com.data.data.hmly.service.ctripcommon.enums.AddInfoDetailType;
import com.data.data.hmly.service.ctripcommon.enums.OrderStatus;
import com.data.data.hmly.service.ctripcommon.enums.RowStatus;
import com.data.data.hmly.service.ctripcommon.enums.TagTargetType;
import com.data.data.hmly.service.nctripticket.dao.CtripAddInfoDetailDao;
import com.data.data.hmly.service.nctripticket.dao.CtripDisplayTagDao;
import com.data.data.hmly.service.nctripticket.dao.CtripDisplayTagGroupDao;
import com.data.data.hmly.service.nctripticket.dao.CtripOrderContactInfoDao;
import com.data.data.hmly.service.nctripticket.dao.CtripOrderFormInfoDao;
import com.data.data.hmly.service.nctripticket.dao.CtripOrderFormResourceInfoDao;
import com.data.data.hmly.service.nctripticket.dao.CtripOrderPassengerInfoDao;
import com.data.data.hmly.service.nctripticket.dao.CtripProductAddInfoDao;
import com.data.data.hmly.service.nctripticket.dao.CtripResourceAddInfoDao;
import com.data.data.hmly.service.nctripticket.dao.CtripResourcePriceCalendarDao;
import com.data.data.hmly.service.nctripticket.dao.CtripScenicSpotCityInfoDao;
import com.data.data.hmly.service.nctripticket.dao.CtripScenicSpotInfoDao;
import com.data.data.hmly.service.nctripticket.dao.CtripScenicSpotPoiInfoDao;
import com.data.data.hmly.service.nctripticket.dao.CtripScenicSpotProductDao;
import com.data.data.hmly.service.nctripticket.dao.CtripScenicSpotResourceDao;
import com.data.data.hmly.service.nctripticket.entity.CtripAddInfoDetail;
import com.data.data.hmly.service.nctripticket.entity.CtripDisplayTag;
import com.data.data.hmly.service.nctripticket.entity.CtripDisplayTagGroup;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderContactInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderFormInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderFormResourceInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderPassengerInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripProductAddInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripResourceAddInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripResourcePriceCalendar;
import com.data.data.hmly.service.nctripticket.entity.CtripScenicSpotCityInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripScenicSpotInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripScenicSpotPoiInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripScenicSpotProduct;
import com.data.data.hmly.service.nctripticket.entity.CtripScenicSpotResource;
import com.data.data.hmly.util.GenCtripOrderNo;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 携程数据保存及转换，
 * Created by caiys on 2016/1/26.
 */
@Service
public class CtripTicketService {
    @Resource
    private CtripScenicSpotInfoDao ctripScenicSpotInfoDao;
    @Resource
    private CtripDisplayTagGroupDao ctripDisplayTagGroupDao;
    @Resource
    private CtripDisplayTagDao ctripDisplayTagDao;
    @Resource
    private CtripScenicSpotPoiInfoDao ctripScenicSpotPoiInfoDao;
    @Resource
    private CtripScenicSpotCityInfoDao ctripScenicSpotCityInfoDao;
    @Resource
    private CtripScenicSpotProductDao ctripScenicSpotProductDao;
    @Resource
    private CtripProductAddInfoDao ctripProductAddInfoDao;
    @Resource
    private CtripAddInfoDetailDao ctripAddInfoDetailDao;
    @Resource
    private CtripScenicSpotResourceDao ctripScenicSpotResourceDao;
    @Resource
    private CtripResourceAddInfoDao ctripResourceAddInfoDao;
    @Resource
    private CtripResourcePriceCalendarDao ctripResourcePriceCalendarDao;
    @Resource
    private CtripOrderContactInfoDao ctripOrderContactInfoDao;
    @Resource
    private CtripOrderFormInfoDao ctripOrderFormInfoDao;
    @Resource
    private CtripOrderFormResourceInfoDao ctripOrderFormResourceInfoDao;
    @Resource
    private CtripOrderPassengerInfoDao ctripOrderPassengerInfoDao;
    @Resource
    private PropertiesManager propertiesManager;

    /**
     * 门票景点数据更新（包括景点相关信息、门票资源等），
     * 景点数据只做新增修改，不做删除，若存在状态为RowStatus.UPDATE，不存在状态为RowStatus.INSERT
     * 子表数据先删除再插入
     *
     * @param scenicSpotInfos 门票景点数据（包括景点相关信息、门票资源等），
     */
    public void updateScenicSpotInfo(List<CtripScenicSpotInfo> scenicSpotInfos) {
        for (CtripScenicSpotInfo ssi : scenicSpotInfos) {
            for (CtripScenicSpotResource ssr : ssi.getResourceList()) {
                Criteria<CtripScenicSpotResource> criteria = new Criteria<>(CtripScenicSpotResource.class);
                criteria.eq("ctripResourceId", ssr.getCtripResourceId());
                CtripScenicSpotResource ctripScenicSpotResource = ctripScenicSpotResourceDao.findUniqueByCriteria(criteria);
                if (ctripScenicSpotResource == null) {  // 不存在
                    ssr.setRowStatus(RowStatus.INSERT);
                    ssr.setCreateTime(new Date());
                    ctripScenicSpotResourceDao.save(ssr);
                } else {    // 存在，更新数据，同时删除子表数据
                    ctripScenicSpotResource.setRowStatus(RowStatus.UPDATE);
                    ctripScenicSpotResource.setUpdateTime(new Date());
                    ctripScenicSpotResource.setCreateTime(ctripScenicSpotResource.getCreateTime());
                    ctripScenicSpotResourceDao.evict(ctripScenicSpotResource);
                    ctripScenicSpotResourceDao.update(ctripScenicSpotResource);
                }

            }
            // 景点信息处理
            CtripScenicSpotInfo ctripScenicSpotInfo = ctripScenicSpotInfoDao.load(ssi.getId());
            if (ctripScenicSpotInfo == null) {  // 不存在
                ssi.setRowStatus(RowStatus.INSERT);
                ssi.setCreateTime(new Date());
                ctripScenicSpotInfoDao.save(ssi);
            } else {    // 存在，更新数据，同时删除子表数据
                ssi.setRowStatus(RowStatus.UPDATE);
                ssi.setUpdateTime(new Date());
                ssi.setCreateTime(ctripScenicSpotInfo.getCreateTime());
//                ctripScenicSpotInfoDao.deleteRelationInfo(ssi.getId());
                ctripScenicSpotInfoDao.evict(ctripScenicSpotInfo);
                ctripScenicSpotInfoDao.update(ssi);
            }


            // 景点标签组、标签
           /* if (ssi.getDisplayTagGroupList() != null) {
                for (CtripDisplayTagGroup dtg : ssi.getDisplayTagGroupList()) {
                    dtg.setTargetId(ssi.getId());
                    dtg.setGroupKey(dtg.getKey());
                    dtg.setTargetType(TagTargetType.SCENIC);
                    dtg.setRowStatus(RowStatus.INSERT);
                    dtg.setCreateTime(new Date());
                    ctripDisplayTagGroupDao.save(dtg);

                    // 标签
                    if (dtg.getDisplayTagList() != null) {
                        for (CtripDisplayTag dt : dtg.getDisplayTagList()) {
                            dt.setDisplayTagGroupId(dtg.getId());
                            dt.setTagKey(dt.getKey());
                            dt.setTagValue(dt.getValue());
                            dt.setRowStatus(RowStatus.INSERT);
                            dt.setCreateTime(new Date());
                            ctripDisplayTagDao.save(dt);
                        }
                    }
                }
            }*/

            // poi信息
      /*      CtripScenicSpotPoiInfo sspi = ssi.getPoiInfo();
            if (sspi != null) {
                sspi.setId(ssi.getId());
                sspi.setRowStatus(RowStatus.INSERT);
                sspi.setCreateTime(new Date());
                ctripScenicSpotPoiInfoDao.save(sspi);
            }
            // 城市信息
            CtripScenicSpotCityInfo ssci = ssi.getCityInfo();
            if (ssci != null) {
                ssci.setId(ssi.getId());
                ssci.setRowStatus(RowStatus.INSERT);
                ssci.setCreateTime(new Date());
                ctripScenicSpotCityInfoDao.save(ssci);
            }

            // 产品信息
            CtripScenicSpotProduct ssp = ssi.getProductInfo();
            if (ssp != null) {
                ssp.setId(ssi.getId());
                ssp.setRowStatus(RowStatus.INSERT);
                ssp.setCreateTime(new Date());
                ctripScenicSpotProductDao.save(ssp);

                // 产品附加信息
                if (ssp.getProductAddInfoList() != null) {
                    saveProductAddInfos(ssp.getProductAddInfoList(), ssp.getId());
                }

                // 门票资源信息
                if (ssp.getResourceList() != null) {
                    saveResources(ssp.getResourceList(), ssp.getId());
                }
            }*/

        }
    }

    /**
     * 保存产品附加信息、附加信息描述
     *
     * @param pais
     */
    public void saveProductAddInfos(List<CtripProductAddInfo> pais, Long productId) {
        for (CtripProductAddInfo pai : pais) {
            pai.setProductId(productId);
            pai.setRowStatus(RowStatus.INSERT);
            pai.setCreateTime(new Date());
            ctripProductAddInfoDao.save(pai);

            // 附加信息描述
            if (pai.getProductAddInfoDetailList() != null) {
                for (CtripAddInfoDetail aid : pai.getProductAddInfoDetailList()) {
                    aid.setTargetId(pai.getId());
                    aid.setTargetType(AddInfoDetailType.PRODUCT);
                    aid.setRowStatus(RowStatus.INSERT);
                    aid.setCreateTime(new Date());
                    ctripAddInfoDetailDao.save(aid);
                }
            }
        }
    }

    /**
     * 保存门票资源信息、标签组、标签集合、附加信息、附加信息描述
     *
     * @param ssrs
     * @param productId
     */
    public void saveResources(List<CtripScenicSpotResource> ssrs, Long productId) {
        for (CtripScenicSpotResource ssr : ssrs) {
            ssr.setCtripResourceId(ssr.getId());
            ssr.setId(null);    // 替换携程门票资源ID
            ssr.setProductId(productId);
            ssr.setFirstBooking(convertStr(ssr.getFirstBookingDate()));
            ssr.setRowStatus(RowStatus.INSERT);
            ssr.setCreateTime(new Date());
            ctripScenicSpotResourceDao.save(ssr);

            // 标签组
            if (ssr.getDisplayTagGroupList() != null) {
                for (CtripDisplayTagGroup dtg : ssr.getDisplayTagGroupList()) {
                    dtg.setTargetId(ssr.getId());
                    dtg.setGroupKey(dtg.getKey());
                    dtg.setTargetType(TagTargetType.TICKET_RESCOURCE);
                    dtg.setRowStatus(RowStatus.INSERT);
                    dtg.setCreateTime(new Date());
                    ctripDisplayTagGroupDao.save(dtg);

                    // 标签
                    if (dtg.getDisplayTagList() != null) {
                        for (CtripDisplayTag dt : dtg.getDisplayTagList()) {
                            dt.setDisplayTagGroupId(dtg.getId());
                            dt.setTagKey(dt.getKey());
                            dt.setTagValue(dt.getValue());
                            dt.setRowStatus(RowStatus.INSERT);
                            dt.setCreateTime(new Date());
                            ctripDisplayTagDao.save(dt);
                        }
                    }
                }
            }

            // 附加信息
            if (ssr.getResourceAddInfoList() != null) {
                for (CtripResourceAddInfo rai : ssr.getResourceAddInfoList()) {
                    rai.setResourceId(ssr.getId());
                    rai.setRowStatus(RowStatus.INSERT);
                    rai.setCreateTime(new Date());
                    ctripResourceAddInfoDao.save(rai);

                    // 附加信息描述
                    if (rai.getResourceAddInfoDetailList() != null) {
                        for (CtripAddInfoDetail aid : rai.getResourceAddInfoDetailList()) {
                            aid.setTargetId(rai.getId());
                            aid.setTargetType(AddInfoDetailType.TICKET_RESCOURCE);
                            aid.setRowStatus(RowStatus.INSERT);
                            aid.setCreateTime(new Date());
                            ctripAddInfoDetailDao.save(aid);
                        }
                    }
                }
            }
        }
    }

    /**
     * 日期字符串转换为日期类型，日期字符串格式如：\/Date(1454312700000-0000)\/
     *
     * @param dateStr
     * @return
     */
    public Date convertStr(String dateStr) {
        if (StringUtils.isNotBlank(dateStr)) {
            Pattern p = Pattern.compile("\\d+");
            Matcher m = p.matcher(dateStr);
            if (m.find()) {
                Date date = new Date(Long.valueOf(m.group()));
                return date;
            }
        }
        return null;
    }

    /**
     * 更新门票价格日历，先把记录删除，再做插入
     *
     * @param rpcs
     * @param startDate
     * @param endDate
     */
/*   public void updateResourcePriceCalendar(List<CtripResourcePriceCalendar> rpcs, List<CtripScenicSpotResource> resources, Date startDate, Date endDate) {
        List<Long> resourceIdList = new ArrayList<Long>();
       Map<Long, List<CtripScenicSpotResource>> recIdMap = new HashMap<Long, List<CtripScenicSpotResource>>();   // 处理一个携程门票资源对应多个本地门票资源
        for (CtripScenicSpotResource r : resources) {
            resourceIdList.add(r.getId());
            if (recIdMap.get(r.getCtripResourceId()) != null) {
                recIdMap.get(r.getCtripResourceId()).add(r);
            } else {
                List<CtripScenicSpotResource> recIdList = new ArrayList<CtripScenicSpotResource>();
                recIdList.add(r);
                recIdMap.put(r.getCtripResourceId(), recIdList);
            }
        }*/
    public void updateResourcePriceCalendar(List<CtripResourcePriceCalendar> rpcs, List<CtripScenicSpotResource> resources, Date startDate, Date endDate) {
        //     ctripResourcePriceCalendarDao.delByResourceIdAndDay(resources, startDate, endDate);
        List<Long> resourceIdList = new ArrayList<Long>();
        Map<Long, List<CtripScenicSpotResource>> recIdMap = new HashMap<Long, List<CtripScenicSpotResource>>();   // 处理一个携程门票资源对应多个本地门票资源
        for (CtripScenicSpotResource r : resources) {
            resourceIdList.add(r.getCtripResourceId());
            if (recIdMap.get(r.getCtripResourceId()) != null) {
                recIdMap.get(r.getCtripResourceId()).add(r);
            } else {
                List<CtripScenicSpotResource> recIdList = new ArrayList<CtripScenicSpotResource>();
                recIdList.add(r);
                recIdMap.put(r.getCtripResourceId(), recIdList);
            }
        }
        ctripResourcePriceCalendarDao.delByResourceIdAndDay(resources, startDate, endDate);
        for (CtripResourcePriceCalendar rpc : rpcs) {
//            Criteria<CtripResourcePriceCalendar> criteria = new Criteria<>(CtripResourcePriceCalendar.class);
//            criteria.eq("resourceId", rpc.getResourceId());
            // CtripResourcePriceCalendar ctripResourcePriceCalendar = ctripResourcePriceCalendarDao.findAll();
            //  if (ctripResourcePriceCalendar == null) {  // 不存在
            rpc.setRowStatus(RowStatus.INSERT);
            rpc.setCreateTime(new Date());
            ctripResourcePriceCalendarDao.save(rpc);
            //  }
            //   else {    // 存在，更新数据，同时删除子表数据
//                    ctripResourcePriceCalendar.setRowStatus(RowStatus.UPDATE);
//                    ctripResourcePriceCalendar.setUpdateTime(new Date());
//                    ctripResourcePriceCalendar.setCreateTime(ctripResourcePriceCalendar.getCreateTime());

//                    ctripResourcePriceCalendarDao.evict(ctripResourcePriceCalendar);
            //    ctripResourcePriceCalendarDao.update(ctripResourcePriceCalendar);
        }
    }
    // }
//            /*List<CtripScenicSpotResource> recList = recIdMap.get(rpc.getCtripResourceId());
//            if (recList == null || recList.isEmpty()) {
//                continue;
//            }
          /*  for (CtripScenicSpotResource res : recList) {
                CtripResourcePriceCalendar ctripResourcePriceCalendar = new CtripResourcePriceCalendar();
//                ctripResourcePriceCalendar.setResourceId(res.getId());
//                ctripResourcePriceCalendar.setCtripResourceId(rpc.getCtripResourceId());
//                ctripResourcePriceCalendar.setCtripProductId(res.getProductId());
//                ctripResourcePriceCalendar.setPriceDate(convertStr(rpc.getDate()));
//                ctripResourcePriceCalendar.setPrice(rpc.getPrice());
//                ctripResourcePriceCalendar.setMarketPrice(rpc.getMarketPrice());
//                ctripResourcePriceCalendar.setCtripPrice(rpc.getCtripPrice());
                ctripResourcePriceCalendar.setRowStatus(RowStatus.INSERT);
                ctripResourcePriceCalendar.setCreateTime(new Date());
                ctripResourcePriceCalendarDao.save(ctripResourcePriceCalendar);
            }*/
       /* }
    }*/


    /**
     * 删除景点及相关子表信息
     *
     * @param scenicSpotId
     */
    public void deleteRelationInfo(Long scenicSpotId) {
        ctripScenicSpotInfoDao.deleteRelationInfo(scenicSpotId);
    }

    /**
     * 统计门票资源数量
     */
    public Long countCtripScenicSpotResource() {
        return ctripScenicSpotResourceDao.countCtripScenicSpotResource();
    }

    /**
     * 分页查询
     */
    public List<CtripScenicSpotResource> listCtripScenicSpotResource(Integer pageIndex, Integer pageSize) {
        return ctripScenicSpotResourceDao.listCtripScenicSpotResourceId(pageIndex, pageSize);
    }

    /**
     * 根据携程资源标识查询资源信息
     */
    public List<CtripScenicSpotResource> listResourceBy(List<Long> ctripResourceIdList) {
        return ctripScenicSpotResourceDao.listResourceBy(ctripResourceIdList);
    }

    /**
     * 更新携程同步的门票数据到本地数据结构
     * 要求：scenic表的ctrip_scenic_id需已赋值
     *
     * @author caiys
     * @date 2015年12月29日 下午5:25:42
     */
    public void updateTicket() {
        ctripScenicSpotInfoDao.updateTicket();
    }

    /**
     * 更新携程同步的门票价格数据到本地数据结构
     *
     * @author caiys
     * @date 2015年12月29日 下午5:56:25
     */
    public void updateTicketDatePrice(Date startDate, Date endDate) {
        ctripScenicSpotInfoDao.updateTicketDatePrice(startDate, endDate);
    }

    /**
     * 保存订单信息
     *
     * @param resourceInfoList
     * @param ctripOrderContactInfo
     * @param ctripOrderPassengerInfos
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CtripOrderFormInfo saveOrderInfo(Long scenicSpotId, List<CtripOrderFormResourceInfo> resourceInfoList, CtripOrderContactInfo ctripOrderContactInfo, List<CtripOrderPassengerInfo> ctripOrderPassengerInfos) {
        // 总人数和总订单价
        int peopleNumber = 0;
        float amount = 0f;
        for (CtripOrderFormResourceInfo resourceInfo : resourceInfoList) {
            peopleNumber = peopleNumber + resourceInfo.getQuantity();
            amount = amount + resourceInfo.getPrice() * resourceInfo.getQuantity();
        }

        // 订单信息
        CtripOrderFormInfo ctripOrderFormInfo = new CtripOrderFormInfo();
        ctripOrderFormInfo.setScenicSpotId(scenicSpotId);
        ctripOrderFormInfo.setPayMode("P");
        ctripOrderFormInfo.setThirdPayType(1);
        ctripOrderFormInfo.setPeopleNumber(peopleNumber);
        ctripOrderFormInfo.setAmount(amount); // 分销价总和
        ctripOrderFormInfo.setServerFrom("旅行帮");
        ctripOrderFormInfo.setUid(propertiesManager.getString("CTRIP_UID"));  // 用户接口生成
        ctripOrderFormInfo.setOrderStatus(OrderStatus.CREATE);
        ctripOrderFormInfo.setCreateTime(new Date());
        ctripOrderFormInfoDao.save(ctripOrderFormInfo);
        String ctripOrderNo = GenCtripOrderNo.generate(propertiesManager.getString("MACHINE_NO", ""));
        ctripOrderFormInfo.setDistributorOrderId(ctripOrderNo);    // 每次必须唯一（即使是重新下单）
        ctripOrderFormInfo.setResourceInfoList(resourceInfoList);

        // 资源列表
        for (CtripOrderFormResourceInfo resourceInfo : resourceInfoList) {
            resourceInfo.setOrderFormInfoId(ctripOrderFormInfo.getId());
            ctripOrderFormResourceInfoDao.save(resourceInfo);
        }

        // 订单联系人信息
        ctripOrderContactInfo.setOrderFormInfoId(ctripOrderFormInfo.getId());
        ctripOrderContactInfoDao.save(ctripOrderContactInfo);

        // 旅客信息列表
        for (CtripOrderPassengerInfo passengerInfo : ctripOrderPassengerInfos) {
            passengerInfo.setOrderFormInfoId(ctripOrderFormInfo.getId());
            ctripOrderPassengerInfoDao.save(passengerInfo);
        }

        return ctripOrderFormInfo;
    }

    /**
     * 回写订单状态
     */
    public void updateOrderStatus(Long ctripOrderId, OrderStatus orderStatus) {
        CtripOrderFormInfo ctripOrderFormInfo = ctripOrderFormInfoDao.findUniqueBy(ctripOrderId);
        ctripOrderFormInfo.setOrderStatus(orderStatus);
        ctripOrderFormInfo.setUpdateTime(new Date());
        ctripOrderFormInfoDao.update(ctripOrderFormInfo);
    }

    /**
     * 回写订单状态并设置退单处理时间
     */
    public void updateOrderStatus(Long ctripOrderId, OrderStatus orderStatus, Date cancelHandleTime) {
        CtripOrderFormInfo ctripOrderFormInfo = ctripOrderFormInfoDao.findUniqueBy(ctripOrderId);
        ctripOrderFormInfo.setOrderStatus(orderStatus);
        ctripOrderFormInfo.setCancelHandleTime(cancelHandleTime);
        ctripOrderFormInfoDao.update(ctripOrderFormInfo);
    }

    /**
     * 更新原始订单取消状态
     *
     * @param ctripOrderId
     */
    public void updateOriginalOrderCanceled(Long ctripOrderId) {
        ctripOrderFormInfoDao.updateOriginalOrderCanceled(ctripOrderId);
    }

    /**
     * 分页查询
     */
    public List<CtripOrderFormInfo> listCtripOrderFormInfo(CtripOrderFormInfo ctripOrderFormInfo, Page page) {
        return ctripOrderFormInfoDao.listCtripOrderFormInfo(ctripOrderFormInfo, page);
    }

    /**
     * 携程门票资源如果不存在，则清除对应的价格日历数据
     */
    public void doClearPriceNotResource() {
        ctripResourcePriceCalendarDao.doClearPriceNotResource();
    }

    /**
     * 查询区域景点的携程标识
     */
    public List<Long> findCtripScenicIdList(Long cityId) {
        return ctripScenicSpotInfoDao.findCtripScenicIdList(cityId);
    }

    //门票ID列表查询
    public List<Long> getTicketIdList() {
        List<CtripScenicSpotResource> ctripScenicSpotResources = ctripScenicSpotResourceDao.findAll();
        List<Long> ticketIdList = new ArrayList<>();
        for (CtripScenicSpotResource ticketResource : ctripScenicSpotResources) {
            Long id = ticketResource.getCtripResourceId();
            ticketIdList.add(id);
        }
        return ticketIdList;
    }
}
