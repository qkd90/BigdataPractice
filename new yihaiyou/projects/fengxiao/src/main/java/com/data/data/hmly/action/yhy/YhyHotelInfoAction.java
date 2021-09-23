package com.data.data.hmly.action.yhy;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.enums.ShowStatus;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.hotel.HotelCityServiceService;
import com.data.data.hmly.service.hotel.HotelExtendService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelCityService;
import com.data.data.hmly.service.hotel.entity.HotelExtend;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.BeanUtils;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/11/21.
 */
public class YhyHotelInfoAction extends FrameBaseAction {

    private Map<String, Object> result = new HashMap<String, Object>();

    private Long id;
    private Hotel hotel = new Hotel();
    private HotelExtend hotelExtend = new HotelExtend();
    private Productimage productimage = new Productimage();
    private List<Productimage> imgList = new ArrayList<Productimage>();

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;


    @Resource
    private HotelService hotelService;
    @Resource
    private HotelExtendService hotelExtendService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private ProductService productService;
    @Resource
    private HotelCityServiceService hotelCityServiceService;

    public Result toHotelInfo() {
        if (id != null) {
            ShowStatus showStatus = productService.getShowStatus(id);
            if (showStatus == null || ShowStatus.HIDE_FOR_CHECK.equals(showStatus)) {
                return redirect("/yhy/yhyMain/toHomestayList.jhtml");
            }
            getSession().setAttribute("editHotelId", id);
        } else {
            getSession().removeAttribute("editHotelId");
        }
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_info.jsp");
    }

    public Result toHotelInfoDetail() {
        if (id != null) {
            ProductStatus status = productService.getProStatus(id);
            if (status != null && !ProductStatus.DEL.equals(status)) {
                getSession().setAttribute("hotelId", id);
                return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_info_detail.jsp");
            }
        }
        getSession().removeAttribute("hotelId");
        return redirect("/yhy/yhyMain/toHomestayList.jhtml");
    }

    public Result getHotelServiceAmenities() {
        // hotel service amenities
        List<HotelCityService> serviceList = hotelCityServiceService.listByCity(350200);
        // put hotel serviceList
        result.put("serviceList", serviceList);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result getYhyHotelInfo() {
        if (id != null) {
            // hotel / extend info
            Hotel hotel = hotelService.get(id);
            HotelExtend extend = hotel.getExtend();
            // image data / just load origin image data
            List<Productimage> productimageList = productimageService.findAllImagesByProIdAadTarId(hotel.getId(), hotel.getId(), "hotel/hotelDesc/", "showOrder");
            Class productClazz = hotel.getClass().getSuperclass();
            Method[] methods = productClazz.getMethods();
            Field[] fields = hotel.getClass().getDeclaredFields();
            Field[] extendFields = extend.getClass().getDeclaredFields();
            try {
                // reflect product info to map
                for (Method method : methods) {
                    if (method.getName().startsWith("get")) {
                        String fName = method.getName().substring(3, method.getName().length());
                        String fieldName = fName.substring(0, 1).toLowerCase() + fName.substring(1);
                        result.put("hotel." + fieldName, method.invoke(hotel));
                    }
                }
                // reflect hotel info to map
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (!"extend".equals(field.getName())) {
                        result.put("hotel." + field.getName(), field.get(hotel));
                    }
                }
                // reflect hotel extend info to map
                for (Field field : extendFields) {
                    field.setAccessible(true);
                    result.put("hotelExtend." + field.getName(), field.get(extend));
                }
                // put image data
                result.put("imgData", productimageList);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                result.put("success", false);
                result.put("msg", "数据获取错误!");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                result.put("success", false);
                result.put("msg", "数据获取错误!");
            }
            result.put("success", true);
            result.put("msg", "");
            JsonConfig jsonConfig = JsonFilter.getIncludeConfig("extend");
            return json(JSONObject.fromObject(result, jsonConfig));
        }
        result.put("success", false);
        result.put("msg", "产品id错误!");
        return json(JSONObject.fromObject(result));
    }

    public Result getYhyHotelList() {
        SysUser loginUser = getLoginUser();
        SysUnit companyUnit = loginUser.getSysUnit().getCompanyUnit();
        Integer pageIndex = start / length + 1;
        Page page = new Page(pageIndex, length);
        List<Hotel> hotelList = hotelService.getByCompanyUnit(hotel, companyUnit, page);
        List<Hotel> resultHotelList = Lists.transform(hotelList, new Function<Hotel, Hotel>() {
            @Override
            public Hotel apply(Hotel hotel) {
                if (hotel.getProductimage() != null && hotel.getProductimage().size() > 0) {
                    hotel.setImageTotalCount(hotel.getProductimage().size());
                }
                return hotel;
            }
        });
        result.put("data", resultHotelList);
        result.put("recordsTotal", page.getTotalCount());
        result.put("recordsFiltered", page.getTotalCount());
        result.put("draw", draw);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("extend");
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result saveHotelInfo() {
        SysUser loginUser = getLoginUser();
        if (hotel.getId() != null) {
            ProductStatus status = productService.getProStatus(hotel.getId());
            // edit source data (without temp data)
            if (hotel.getOriginId() == null && ProductStatus.UP.equals(status)) {
                // source hotel info
                Hotel sourceHotel = hotelService.get(hotel.getId());
                HotelExtend sourceExtend = sourceHotel.getExtend();
                sourceHotel.setShowStatus(ShowStatus.HIDE_FOR_CHECK);
                sourceHotel.setUpdateTime(new Date());
                // create temp hotel / extend
                Hotel tempHotel = new Hotel();
                HotelExtend tempExtend = new HotelExtend();
                // copy source hotel / extend info to tempHotel / tempExtend
                BeanUtils.copyProperties(sourceHotel, tempHotel, false, null, "id", "hotelPriceList", "labelItems", "productimage", "commentList");
                BeanUtils.copyProperties(sourceExtend, tempExtend, false, null, "id");
                // copy update hotel / extend info to tempHotel / tempExtend
                BeanUtils.copyProperties(hotel, tempHotel, false, null, "id", "hotelPriceList", "labelItems", "productimage", "commentList");
                BeanUtils.copyProperties(hotelExtend, tempExtend, false, null, "id");
                // set origin hotel info
                tempHotel.setOriginId(sourceHotel.getId());
                tempHotel.setShowStatus(ShowStatus.SHOW);
                tempHotel.setStatus(ProductStatus.UP_CHECKING);
                tempHotel.setCreateTime(new Date());
                tempHotel.setUpdateTime(new Date());
                // update source hotel
                hotelService.update(sourceHotel);
                // save temp hotel / extend
                hotelService.save(tempHotel);
                tempExtend.setHotel(tempHotel);
                tempExtend.setId(tempHotel.getId());
                hotelExtendService.save(tempExtend);
                // handle product images
                // del old UP_CHECKING img
//                productimageService.delImages(sourceHotel.getId(), sourceHotel.getId());
                for (Productimage productimage : imgList) {
                    productimage.setId(null);
                    productimage.setProduct(tempHotel);
                    productimage.setTargetId(tempHotel.getId());
                    productimage.setCreateTime(new Date());
                    if (productimage.getShowOrder() == null) {
                        productimage.setShowOrder(999);
                    }
                    productimage.setCompanyUnitId(loginUser.getSysUnit().getCompanyUnit().getId());
                }
                productimageService.saveAll(imgList);
            } else if (hotel.getOriginId() != null || !ProductStatus.UP.equals(status)) {
                // edit temp data
                Hotel tempHotel = hotelService.get(hotel.getId());
                HotelExtend tempExtend = tempHotel.getExtend();
                // source hotel
                Hotel sourceHotel;
                if (hotel.getOriginId() != null) {
                    sourceHotel = new Hotel();
                    sourceHotel.setId(tempHotel.getOriginId());
                } else {
                    sourceHotel = tempHotel;
                }
                // copy update hotel / extend info to tempHotel / tempExtend
                BeanUtils.copyProperties(hotel, tempHotel, false, null, "id", "hotelPriceList", "labelItems", "productimage", "commentList");
                BeanUtils.copyProperties(hotelExtend, tempExtend, false, null, "id");
                tempHotel.setStatus(ProductStatus.UP_CHECKING);
                tempHotel.setUpdateTime(new Date());
                // update tempHotel / tempExtend
                hotelService.update(tempHotel);
                hotelExtendService.update(tempExtend);
                // handle product images
                // del old UP_CHECKING img
                productimageService.delImages(tempHotel.getId(), tempHotel.getId(), "hotel/hotelDesc/");
                for (Productimage productimage : imgList) {
                    productimage.setId(null);
                    productimage.setProduct(tempHotel);
                    productimage.setTargetId(tempHotel.getId());
                    productimage.setCreateTime(new Date());
                    if (productimage.getShowOrder() == null) {
                        productimage.setShowOrder(999);
                    }
                    productimage.setCompanyUnitId(loginUser.getSysUnit().getCompanyUnit().getId());
                }
                productimageService.saveAll(imgList);
            }
        } else {
            // new hotel data
            hotel.setStatus(ProductStatus.UP_CHECKING);
            hotel.setShowStatus(ShowStatus.SHOW);
            hotel.setUser(loginUser);
            hotel.setCompanyUnit(loginUser.getSysUnit().getCompanyUnit());
            hotel.setProType(ProductType.hotel);
            hotel.setTopProduct(hotel);
            hotel.setSupplier(loginUser);
            hotel.setSource(ProductSource.LXB);
            hotel.setNeedConfirm(false);
            hotel.setShowOrder(999);
            hotel.setCreateTime(new Date());
            hotel.setUpdateTime(new Date());
            // save hotel
            hotelService.save(hotel);
            // save hotel extend
            hotelExtend.setHotel(hotel);
            hotelExtend.setId(hotel.getId());
            hotelExtendService.save(hotelExtend);

            int index = 1;
            for (Productimage productimage : imgList) {
                productimage.setId(null);
                productimage.setProduct(hotel);
                productimage.setTargetId(hotel.getId());
                productimage.setCreateTime(new Date());
                productimage.setShowOrder(index);
                productimage.setCompanyUnitId(loginUser.getSysUnit().getCompanyUnit().getId());
                index ++;
            }
            productimageService.saveAll(imgList);
        }
        result.put("success", true);
        result.put("msg", "");
        return json(JSONObject.fromObject(result));
    }

    public Result revokeHotel() {
        if (id != null) {
            Hotel hotel = hotelService.get(id);
            if (hotel != null) {
                ProductStatus status = hotel.getStatus();
                Long originId = hotel.getOriginId();
                if (ProductStatus.UP_CHECKING.equals(status) || ProductStatus.REFUSE.equals(status)) {
                    // temp data
                    if (originId != null) {
                        // delete temp data
                        HotelExtend tempExtend = hotel.getExtend();
                        hotelExtendService.delete(tempExtend);
                        hotelService.delete(hotel);
                        // update origin data
                        Hotel sourceHotel = hotelService.get(originId);
                        sourceHotel.setShowStatus(ShowStatus.SHOW);
                        hotelService.update(sourceHotel);
                        // handle product image
                        // delete UP_CHECKING image
                        productimageService.delImages(hotel.getId(), hotel.getId(), "hotel/hotelDesc/");
                    } else {
                        // update source data
                        // back to DOWN status
                        hotel.setStatus(ProductStatus.DOWN);
                        hotelService.update(hotel);
                    }
                    result.put("success", true);
                    result.put("msg", "操作成功!");
                } else if (ProductStatus.DOWN_CHECKING.equals(status)) {
                    // update source data
                    // back to UP status
                    hotel.setStatus(ProductStatus.UP);
                    hotelService.update(hotel);
                    result.put("success", true);
                    result.put("msg", "操作成功!");
                } else {
                    result.put("success", false);
                    result.put("msg", "产品状态错误! 操作失败!");
                }
            } else {
                result.put("success", false);
                result.put("msg", "民宿id错误! 请检查民宿状态!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "民宿id错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result downHotel() {
        if (id != null) {
            Hotel hotel = hotelService.get(id);
            if (hotel != null && ProductStatus.UP.equals(hotel.getStatus())) {
                hotel.setStatus(ProductStatus.DOWN_CHECKING);
                hotelService.update(hotel);
                result.put("success", true);
                result.put("msg", "操作成功!");
            } else {
                result.put("success", false);
                result.put("msg", "民宿不存在或状态错误!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "民宿id错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result delHotel() {
        if (id != null) {
            Hotel hotel = hotelService.get(id);
            if (hotel != null && ProductStatus.DOWN.equals(hotel.getStatus())) {
                hotel.setStatus(ProductStatus.DEL);
                hotelService.update(hotel);
                result.put("success", true);
                result.put("msg", "操作成功!");
            } else {
                result.put("success", false);
                result.put("msg", "民宿不存在或状态错误!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "民宿id错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public HotelExtend getHotelExtend() {
        return hotelExtend;
    }

    public void setHotelExtend(HotelExtend hotelExtend) {
        this.hotelExtend = hotelExtend;
    }

    public Productimage getProductimage() {
        return productimage;
    }

    public void setProductimage(Productimage productimage) {
        this.productimage = productimage;
    }

    public List<Productimage> getImgList() {
        return imgList;
    }

    public void setImgList(List<Productimage> imgList) {
        this.imgList = imgList;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
