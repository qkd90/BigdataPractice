package com.data.data.hmly.service.hotel;

import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.vo.HotelRoomSolrEntity;
import com.data.data.solr.MulticoreSolrTemplate;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Jonathan.Guo
 */
@Service
public class HotelRoomIndexService {

    private final Logger logger = Logger.getLogger(HotelRoomIndexService.class);

    @Resource
    MulticoreSolrTemplate solrTemplate;
    @Resource
    private HotelService hotelService;
    @Resource
    private HotelPriceService hotelPriceService;


//    public void indexHotelRoom() {
//        logger.info("start index hotel room data to solr");
//        int pageNo = 1;
//        int total = 0;
//        int size = 100;
//        Hotel searchCondition = new Hotel();
//        searchCondition.setStatus(ProductStatus.UP);
//        List<Hotel> hotels;
//        Clock clock = new Clock();
//        List<HotelRoomSolrEntity> unCommitedEntities = Lists.newArrayList();
//        do {
//            Page page = new Page(pageNo, size);
//            hotels = hotelService.listWithoutCount(searchCondition, page);
//            Clock smallClock = new Clock();
//            for (Hotel hotel : hotels) {
//                unCommitedEntities.addAll(getHotelRoomData(hotel));
//                if (unCommitedEntities.size() > 100) {
//                    indexHotelRoom(unCommitedEntities);
//                    unCommitedEntities.clear();
//                    logger.info("indexed 100 hotel rooms cost " + smallClock.elapseTime());
//                }
//            }
//            indexHotelRoom(unCommitedEntities);
//            unCommitedEntities.clear();
//            logger.info("indexed 100 hotel rooms cost " + smallClock.elapseTime());
//            total = page.getTotalCount();
//            logger.info("indexed " + pageNo * size + "/" + total + " hotel, cost " + clock.elapseTime() + "ms");
//            pageNo++;
//            hotelPriceService.clear();
//        } while (hotels.size() == 100);
//    }

//    public void indexHotelRoom(Long hotelId) {
//        Hotel hotel = hotelService.get(hotelId);
//        indexHotelRoom(getHotelRoomData(hotel));
//    }

    private List<HotelRoomSolrEntity> getHotelRoomData(Hotel hotel) {
        HotelPrice priceFilter = new HotelPrice();
        Hotel hotelFilter = new Hotel();
        hotelFilter.setId(hotel.getId());
        priceFilter.setHotel(hotelFilter);
        List<HotelPrice> hotelPrices = hotelPriceService.list(priceFilter, null, new Page(0, Integer.MAX_VALUE));
        Map<String, HotelRoomSolrEntity> map = Maps.newHashMap();
        for (HotelPrice hotelPrice : hotelPrices) {
            HotelRoomSolrEntity entity = map.get(hotelPrice.getRatePlanCode());
            if (entity == null) {
                entity = new HotelRoomSolrEntity(hotelPrice);
            }
            entity.addDate(hotelPrice.getDate());
            map.put(hotelPrice.getRatePlanCode(), entity);
        }

        return Lists.newArrayList(map.values());
    }

//    public UpdateResponse indexHotelRoom(List<HotelRoomSolrEntity> hotelRoomSolrEntities) {
//        UpdateResponse response = solrTemplate.saveBeans(hotelRoomSolrEntities);
//        if (response == null) {
//            return response;
//        }
//        response = solrTemplate.commit(SolrIndexService.CORE_HOTEL_PRICE);
//        return response;
//    }

    public List<HotelRoomSolrEntity> findNearHotel(String query, Double longitude, Double latitude, Float distance) {
        SolrQuery solrQuery = new SolrQuery(query);
        QueryResponse response = solrTemplate.nearBy(solrQuery, latitude + "", longitude + "", HotelRoomSolrEntity.class, SolrQuery.ORDER.asc, distance);
        return solrTemplate.convertQueryResponseToBeans(response, HotelRoomSolrEntity.class);
    }

    public List<HotelRoomSolrEntity> findByHotel(String query) {
        QueryResponse response = solrTemplate.query(new SolrQuery(query), "HotelPrice");
        return solrTemplate.convertQueryResponseToBeans(response, HotelRoomSolrEntity.class);
    }

}
