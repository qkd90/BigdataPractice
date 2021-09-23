package com.data.data.hmly.service.hotel;

import com.data.data.hmly.service.hotel.dao.HotelAmenitiesDao;
import com.data.data.hmly.service.hotel.entity.HotelAmenities;
import com.data.data.hmly.service.hotel.entity.vo.AmenititesTree;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelAmenitiesService {

//    Logger logger = Logger.getLogger(RegionService.class);

    @Resource
    private HotelAmenitiesDao hotelAmenitiesDao;

    public void save(HotelAmenities region) {
        hotelAmenitiesDao.save(region);
    }

    public List<HotelAmenities> listByParent(int parent) {
        Criteria<HotelAmenities> criteria = new Criteria<HotelAmenities>(HotelAmenities.class);
        criteria.eq("parent", parent);
        return hotelAmenitiesDao.findByCriteria(criteria);
    }

    public List<HotelAmenities> getListByParent(Integer parentId) {
        Criteria<HotelAmenities> criteria = new Criteria<HotelAmenities>(HotelAmenities.class);
        criteria.eq("parent", parentId);
        return hotelAmenitiesDao.findByCriteria(criteria);
    }

    public List<AmenititesTree> doGetListTree(Integer parentId) {
        List<AmenititesTree> trees = new ArrayList<AmenititesTree>();
        List<HotelAmenities> amenitieses = getListByParent(parentId);
        if (!amenitieses.isEmpty()) {
            for (HotelAmenities hotelAmenities : amenitieses) {
                AmenititesTree tree = new AmenititesTree();
                tree.setId(hotelAmenities.getId());
                tree.setName(hotelAmenities.getName());
                tree.setParentId(parentId);
                List<HotelAmenities> amenitieses1 = getListByParent(hotelAmenities.getId());
                List<AmenititesTree> trees1 = new ArrayList<AmenititesTree>();
                if (!amenitieses1.isEmpty()) {
                    for (HotelAmenities hotelAmenities1 : amenitieses1) {
                        AmenititesTree tree1 = new AmenititesTree();
                        tree1.setId(hotelAmenities1.getId());
                        tree1.setName(hotelAmenities1.getName());
                        tree1.setParentId(hotelAmenities.getId());
                        trees1.add(tree1);
                    }
                    tree.setChildren(trees1);
                }
                trees.add(tree);
            }
        }
        return trees;
    }

    public List<HotelAmenities> listByParent(List<Integer> parentIds) {
        Criteria<HotelAmenities> criteria = new Criteria<HotelAmenities>(HotelAmenities.class);
        criteria.in("parent", parentIds);
        return hotelAmenitiesDao.findByCriteria(criteria);
    }

    public List<HotelAmenities> getListByIds(String idStr) {
        List<Integer> ids = new ArrayList<Integer>();
        String[] idStrArr = idStr.split(",");
        for (String id : idStrArr) {
            id = id.trim();
            ids.add(Integer.parseInt(id));
        }
        Criteria<HotelAmenities> criteria = new Criteria<HotelAmenities>(HotelAmenities.class);
        criteria.in("id", ids);
        return hotelAmenitiesDao.findByCriteria(criteria);

    }

    public String getAmenities(List<HotelAmenities> hotelAmenitieses) {

        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (HotelAmenities amenities : hotelAmenitieses) {

            if (i < hotelAmenitieses.size() - 1) {
                sb.append(amenities.getName());
                sb.append(",");
            } else {
                sb.append(amenities.getName());
            }

            i++;
        }
        return sb.toString();
    }
}
