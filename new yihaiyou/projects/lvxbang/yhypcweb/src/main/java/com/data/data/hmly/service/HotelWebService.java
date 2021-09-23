package com.data.data.hmly.service;

import com.data.data.hmly.action.yhypc.vo.CommentVo;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.hotel.HotelAmenitiesService;
import com.data.data.hmly.service.hotel.HotelAreaService;
import com.data.data.hmly.service.hotel.HotelCityServiceService;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.dao.HotelDao;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelAmenities;
import com.data.data.hmly.service.hotel.entity.HotelArea;
import com.data.data.hmly.service.hotel.entity.HotelCityService;
import com.data.data.hmly.service.hotel.vo.HotelCommentVo;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zzl on 2017/3/8.
 */
@Service
public class HotelWebService {

    @Resource
    private HotelDao hotelDao;
    @Resource
    private HotelAreaService hotelAreaService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private HotelAmenitiesService hotelAmenitiesService;
    @Resource
    private HotelCityServiceService hotelCityServiceService;


    public Hotel findAllDetailById(Long id) {
        Criteria<Hotel> criteria = new Criteria<Hotel>(Hotel.class);
        criteria.createCriteria("hotelPriceList");
        criteria.eq("id", id);
        Hotel hotel = hotelDao.findUniqueByCriteria(criteria);
        if (hotel != null) {
            List<HotelArea> hotelAreas = hotelAreaService.getByHotel(hotel.getId());
            Productimage imageCondition = new Productimage();
            imageCondition.setProduct(hotel);
            if (ProductSource.LXB.equals(hotel.getSource()) || hotel.getSource() == null) {
                imageCondition.setTargetId(hotel.getId());
                imageCondition.setChildFolder("hotel/hotelDesc/");
            }
            imageCondition.setProType(ProductType.hotel);
            List<Productimage> productimages = productimageService.findProductimage(imageCondition, null);
            // min price
            Float minPrice = hotelPriceService.findHotelMinPrice(hotel);
            hotel.setHotelAreas(hotelAreas);
            hotel.setProductimages(productimages);
            if (minPrice != null) {
                if (minPrice < 1) {
                    hotel.setMinPrice(minPrice.toString());
                } else {
                    hotel.setMinPrice(Integer.toString(minPrice.intValue()));
                }
            }
            // Hotel Amenities
            if (StringUtils.hasText(hotel.getServiceAmenities())) {
                List<HotelCityService> cityServices = hotelCityServiceService.listByServiceIdsAndCity(hotel.getServiceAmenities(), 350200);
                String serviceNames = hotelCityServiceService.getServiceNames(cityServices);
                hotel.setServiceNames(serviceNames);
            }
            return hotel;
        } else {
            return null;
        }
    }
}
