package com.data.data.hmly.service;

import com.data.data.hmly.action.mobile.request.ImpressionUpdateRequest;
import com.data.data.hmly.action.mobile.request.Photo;
import com.data.data.hmly.action.mobile.request.PlaceSearchRequest;
import com.data.data.hmly.action.mobile.response.ImpressionResponse;
import com.data.data.hmly.action.mobile.response.PlaceResponse;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.vo.SuggestionEntity;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.data.data.hmly.service.impression.ImpressionGalleryService;
import com.data.data.hmly.service.impression.ImpressionService;
import com.data.data.hmly.service.impression.entity.Impression;
import com.data.data.hmly.service.impression.entity.ImpressionGallery;
import com.data.data.hmly.service.impression.entity.PlaceType;
import com.data.data.hmly.service.restaurant.RestaurantService;
import com.data.data.hmly.service.restaurant.vo.RestaurantSolrEntity;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by huangpeijie on 2016-04-26,0026.
 */
@Service
public class ImpressionMobileService {
    @Resource
    private ImpressionService impressionService;
    @Resource
    private ImpressionGalleryService impressionGalleryService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private HotelService hotelService;
    @Resource
    private RestaurantService restaurantService;

    public Impression saveImpression(ImpressionUpdateRequest impressionUpdateRequest, Member user) {
        Impression impression = null;
        if (impressionUpdateRequest.getId() != null) {
            impression = impressionService.get(impressionUpdateRequest.getId());
        }
        if (impression == null || !impression.getUser().getId().equals(user.getId())) {
            impression = new Impression();
            impression.setCreateTime(new Date());
            impression.setUser(user);
            impression.setCollectNum(0);
            impression.setShareNum(0);
            impression.setBrowsingNum(0);
        }
        requestToImpression(impressionUpdateRequest, impression);
        impression.setModifyTime(new Date());
        impression.setDeleteFlag(0);
        impressionService.save(impression);
        if (impressionUpdateRequest.getGallery() != null && !impressionUpdateRequest.getGallery().isEmpty()) {
            saveImpressionGallery(impressionUpdateRequest.getGallery(), impression);
        }
        return impression;
    }

    private void requestToImpression(ImpressionUpdateRequest impressionUpdateRequest, Impression impression) {
        impression.setPlaceName(impressionUpdateRequest.getPlaceName());
        impression.setPlaceType(impressionUpdateRequest.getPlaceType());
        impression.setTargetId(impressionUpdateRequest.getTargetId());
        impression.setContent(impressionUpdateRequest.getContent());
        impression.setCover(impressionUpdateRequest.getCover());
        impression.setType(impressionUpdateRequest.getType());
    }

    private void saveImpressionGallery(List<Photo> photos, Impression impression) {
        List<Long> galleryIds = Lists.newArrayList();
        if (impression.getImpressionGalleryList() != null) {
            galleryIds = Lists.transform(impression.getImpressionGalleryList(), new Function<ImpressionGallery, Long>() {
                @Override
                public Long apply(ImpressionGallery input) {
                    return input.getId();
                }
            });
        }
        for (Photo photo : photos) {
            ImpressionGallery impressionGallery = null;
            if (photo.getId() != null && photo.getId() > 0) {
                impressionGallery = impressionGalleryService.get(photo.getId());
            }
            if (impressionGallery == null) {
                impressionGallery = new ImpressionGallery();
            }
            photoToImpressionGallery(photo, impressionGallery);
            impressionGallery.setImpression(impression);
            impressionGalleryService.save(impressionGallery);
            galleryIds.remove(impressionGallery.getId());
        }
        for (Long id : galleryIds) {
            impressionGalleryService.deleteById(id);
        }
    }

    private void photoToImpressionGallery(Photo photo, ImpressionGallery impressionGallery) {
        impressionGallery.setId(photo.getId());
        impressionGallery.setImgUrl(photo.getImgUrl());
        impressionGallery.setHeight(photo.getHeight());
        impressionGallery.setWidth(photo.getWidth());
    }

    public ImpressionResponse impressionDetail(Impression impression) {
        ImpressionResponse response = new ImpressionResponse(impression);
        ImpressionGallery gallery = new ImpressionGallery();
        gallery.setImpression(impression);
        List<ImpressionGallery> lists = impressionGalleryService.list(gallery);
        List<Photo> photos = Lists.transform(lists, new Function<ImpressionGallery, Photo>() {
            @Override
            public Photo apply(ImpressionGallery input) {
                return new Photo(input);
            }
        });
        response.setGallery(photos);
        return response;
    }

    public List<PlaceResponse> placeList(PlaceSearchRequest placeSearchRequest, Page page) {
        List<PlaceResponse> list;
        if (PlaceType.all.equals(placeSearchRequest.getType())) {
            List<SuggestionEntity> entities = impressionService.listPlace(placeSearchRequest.getKeyword(), page);
            list = Lists.transform(entities, new Function<SuggestionEntity, PlaceResponse>() {
                @Override
                public PlaceResponse apply(SuggestionEntity input) {
                    return new PlaceResponse(input);
                }
            });
        } else if (PlaceType.scenic.equals(placeSearchRequest.getType())) {
            List<ScenicSolrEntity> entities = scenicInfoService.findNearByScenic(placeSearchRequest.getLongitude(), placeSearchRequest.getLatitude(), 50000f, page);
            list = Lists.transform(entities, new Function<ScenicSolrEntity, PlaceResponse>() {
                @Override
                public PlaceResponse apply(ScenicSolrEntity input) {
                    return new PlaceResponse(input);
                }
            });
        } else if (PlaceType.hotel.equals(placeSearchRequest.getType())) {
            List<HotelSolrEntity> entities = hotelService.findNearByHotel("type:" + SolrType.hotel, placeSearchRequest.getLongitude(), placeSearchRequest.getLatitude(), 50000f, page);
            list = Lists.transform(entities, new Function<HotelSolrEntity, PlaceResponse>() {
                @Override
                public PlaceResponse apply(HotelSolrEntity input) {
                    return new PlaceResponse(input);
                }
            });
        } else if (PlaceType.food.equals(placeSearchRequest.getType())) {
            List<RestaurantSolrEntity> entities = restaurantService.findNearByRestaurant(placeSearchRequest.getLongitude(), placeSearchRequest.getLatitude(), 50000f, page);
            list = Lists.transform(entities, new Function<RestaurantSolrEntity, PlaceResponse>() {
                @Override
                public PlaceResponse apply(RestaurantSolrEntity input) {
                    return new PlaceResponse(input);
                }
            });
        } else {
            list = Lists.newArrayList();
        }
        return list;
    }
}
