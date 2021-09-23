package com.data.data.hmly.service.hotel;

import com.data.data.hmly.service.CtripHotelDescriptionService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.common.entity.AreaRelation;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.ctriphotel.CtripHotelStaticInfoService;
import com.data.data.hmly.service.ctriphotel.PriceInfoService;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotel;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotelDescription;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotelImg;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotelPrice;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.dao.HotelDao;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelExtend;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class HotelCtripService extends SolrIndexService<Hotel, HotelSolrEntity> {

    Logger logger = Logger.getLogger(HotelCtripService.class);
    @Resource
    private HotelDao hotelDao;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private CtripHotelStaticInfoService ctripHotelStaticInfoService;
    @Resource
    private PriceInfoService priceInfoService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private CtripHotelDescriptionService ctripHotelDescriptionService;
    @Resource
    private HotelExtendService hotelExtendService;

    public Hotel getByTargetId(Long targetId) {
        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
        criteria.eq("targetId", targetId);
        try {
            return hotelDao.findUniqueByCriteria(criteria);
        } catch (Exception e) {
            return null;
        }
    }

    // 更新开始前把所有酒店数据设为下架状态
    public void doInvalidCtripHotel(TbArea tbArea) {
        String hql = " update Hotel set status = ? where cityId = ? and source = ?";
        hotelDao.updateByHQL(hql, ProductStatus.DEL, tbArea.getId(), ProductSource.CTRIP);

    }

    // 更新开始前把所有酒店图片数据删除
    public void doInvalidCtripImage(TbArea tbArea) {
//        String hql = " delete Productimage where product.cityId = ? and product.proType = ? and product.source = ?";
//        hotelDao.updateByHQL(hql, tbArea.getId(), ProductType.hotel, ProductSource.CTRIP);

        String sql = "delete img from productimage img, product"
                + " where product.cityId = ? and product.proType = ?"
                + " and product.source = ? and img.productId = product.id";
        hotelDao.updateBySQL(sql, tbArea.getId(), ProductType.hotel, ProductSource.CTRIP);

    }

    // 更新前把所有携程酒店的价格数据删除
    public void doInvalidCtripPrice(TbArea tbArea) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date startDate = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, 28);
        Date endDate = calendar.getTime();
//        String hql = " delete HotelPrice where hotel.cityId = ? and hotel.source = ? and start >= ? and end <= ?";
//        hotelDao.updateByHQL(hql, tbArea.getId(), ProductSource.CTRIP, startDate, endDate);

        String sql = "delete price from hotel_price price, product"
                + " where price.hotelId = product.id and product.cityId = ?"
                + " and product.source = ? and price.start >= ? and price.end <= ?";
        hotelDao.updateBySQL(sql, tbArea.getId(), ProductSource.CTRIP, startDate, endDate);
    }

    // 删除旧数据
    public void doDealOldCtripHotelData(TbArea tbArea) {
        doInvalidCtripHotel(tbArea);
        doInvalidCtripImage(tbArea);
        doInvalidCtripPrice(tbArea);
    }


    public Long doCtripToLxbHotel(AreaRelation areaRelation) {

        Long areaId = areaRelation.getId();
        Long ctripCityId = areaRelation.getCtripHotelCity();

        // delete old data
        TbArea tbArea = new TbArea();
        tbArea.setId(areaId);
        doDealOldCtripHotelData(tbArea);

        Long size = 0L;
        int realSize = 10;
        int pageSize = 10;
        int index = 1;
        while (realSize == pageSize) {
            Page page = new Page(index, pageSize);
            index++;
            List<CtripHotel> ctripHotelList = ctripHotelStaticInfoService.getList(ctripCityId, page);
            realSize = ctripHotelList.size();
            size += realSize;
            for (CtripHotel ctripHotel : ctripHotelList) {
                //
                doCtripToHotel(ctripHotel, areaId);
            }
        }
        return size;
    }

    public Hotel doCtripToHotel(CtripHotel ctripHotel, Long areaId) {
        Boolean newFlag = true;
        Hotel hotel = getByTargetId(ctripHotel.getId());
        HotelExtend hotelExtend = null;
        if (hotel == null) {
            hotel = new Hotel();
            hotel.setCreateTime(new Date());

            hotelExtend = new HotelExtend();
        } else {
            newFlag = false;
            hotel.setUpdateTime(new Date());

            hotelExtend = hotel.getExtend();
        }

        Boolean newExtend = fillHotel(ctripHotel, areaId, hotel, hotelExtend);

        // update img
        List<CtripHotelImg> ctripHotelImgList = ctripHotelStaticInfoService.getImgList(ctripHotel.getId());
        List<Productimage> productImageList = getProductImageList(hotel, ctripHotelImgList);
        if (!productImageList.isEmpty()) {
            // select the forst img as cover
            productImageList.get(0).setCoverFlag(true);
            hotel.setCover(productImageList.get(0).getPath());
            productimageService.saveAll(productImageList);
        }

        if (newFlag) {
            hotelDao.save(hotel);
        } else {
            hotelDao.update(hotel);
        }
        hotel.setTopProduct(hotel);
        // extend info
        if (newExtend) {
            hotelExtend.setId(hotel.getId());
            hotelExtendService.save(hotelExtend);
        } else {
            hotelExtendService.update(hotelExtend);
        }
        hotel.setExtend(hotelExtend);
        hotelDao.update(hotel);

        hotelExtend.setHotel(hotel);

        // update price
        List<HotelPrice> hotelPriceList = new ArrayList<HotelPrice>();
        List<CtripHotelPrice> ctripHotelPriceList = priceInfoService.getList(ctripHotel.getId());
        for (CtripHotelPrice ctripHotelPrice : ctripHotelPriceList) {
            //
            HotelPrice hotelPrice = new HotelPrice();
            hotelPrice.setHotel(hotel);
            hotelPrice.setPrice(ctripHotelPrice.getAmountBeforeTax());
            hotelPrice.setDate(ctripHotelPrice.getStart());
            hotelPrice.setCreateTime(new Date());
            hotelPrice.setModifyTime(new Date());
            hotelPrice.setBreakfast(ctripHotelPrice.getBreakfast());
            hotelPrice.setRatePlanCode(ctripHotelPrice.getRatePlanCode().toString());
            hotelPrice.setStart(ctripHotelPrice.getStart());
            hotelPrice.setEnd(ctripHotelPrice.getEnd());
            hotelPrice.setStatus(PriceStatus.UP);
            hotelPrice.setCancelStart(ctripHotelPrice.getCancelStart());
            hotelPrice.setCancelEnd(ctripHotelPrice.getCancelEnd());
            hotelPrice.setRoomDescription(ctripHotelPrice.getRoomDescription());
            hotelPriceList.add(hotelPrice);
        }
        if (!hotelPriceList.isEmpty()) {
            hotelPriceService.saveAllWithIndex(hotelPriceList);
        }
        return hotel;
    }

    private List<Productimage> getProductImageList(Hotel hotel, List<CtripHotelImg> ctripHotelImgList) {
        List<Productimage> productimageList = new ArrayList<Productimage>();
        for (CtripHotelImg ctripHotelImg : ctripHotelImgList) {
            Productimage productimage = new Productimage();
            productimage.setProduct(hotel);
            productimage.setProType(ProductType.hotel);
            productimage.setPath(ctripHotelImg.getUrl());
            productimage.setCoverFlag(false);
            productimage.setUserId(hotel.getUser().getId());
            productimage.setCompanyUnitId(hotel.getCompanyUnit().getId());
            productimageList.add(productimage);
        }
        return productimageList;
    }

    private Boolean fillHotel(CtripHotel ctripHotel, Long areaId, Hotel hotel, HotelExtend hotelExtend) {
        hotel.setName(ctripHotel.getHotelName());
        hotel.setStatus(ProductStatus.UP);
        SysUser sysUser = new SysUser();
        sysUser.setId(9L);
        hotel.setUser(sysUser);
        SysUnit sysUnit = new SysUnit();
        sysUnit.setId(-1L);
        hotel.setCompanyUnit(sysUnit);
        hotel.setProType(ProductType.hotel);
        hotel.setCityId(areaId);
        hotel.setSupplier(sysUser);
        hotel.setScore(ctripHotel.getCtripUserRate());
        // todo which star
        hotel.setStar(ctripHotel.getHotelStarLicence().intValue());
        hotel.setTargetId(ctripHotel.getId());
        CtripHotelDescription shortHotelDescription = ctripHotelDescriptionService.getShortDescription(ctripHotel.getId());
        if (shortHotelDescription != null && StringUtils.isNotBlank(shortHotelDescription.getDescription())) {
            hotel.setShortDesc(shortHotelDescription.getDescription());
        }
        // TODO hotelextend
        // extend info
        Boolean newExtend = false;
        if (hotelExtend.getId() == null) {
            newExtend = true;
            hotelExtend.setId(hotel.getId());
        }
        hotelExtend.setHotel(hotel);
        hotelExtend.setAddress(ctripHotel.getAddress());
        hotelExtend.setLongitude(ctripHotel.getLongitude());
        hotelExtend.setLatitude(ctripHotel.getLatitude());

        CtripHotelDescription ctripHotelDescription = ctripHotelDescriptionService.getLongDescription(ctripHotel.getId());
        if (ctripHotelDescription != null && StringUtils.isNotBlank(ctripHotelDescription.getDescription())) {
            //
            hotelExtend.setDescription(ctripHotelDescription.getDescription());
        }
        return newExtend;
    }

}
