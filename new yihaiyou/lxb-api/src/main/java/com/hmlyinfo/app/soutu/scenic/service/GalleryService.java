package com.hmlyinfo.app.soutu.scenic.service;

import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.scenic.domain.Gallery;
import com.hmlyinfo.app.soutu.scenic.domain.GalleryImage;
import com.hmlyinfo.app.soutu.scenic.mapper.GalleryImageMapper;
import com.hmlyinfo.app.soutu.scenic.mapper.GalleryMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.Validate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GalleryService extends BaseService<Gallery, Long> {

	@Autowired
	private GalleryMapper<Gallery> mapper;
	@Autowired
	private GalleryImageMapper<GalleryImage> galleryImageMapper;


	@Override
	public BaseMapper<Gallery> getMapper() {
		return mapper;
	}

	@Override
	public String getKey() {
		return "id";
	}
	
	/**
	 * 根据scenicId，找到galleryId
	 * <ul>
	 * <li>必选：景点id{scenicId}</li>
	 * </ul>
	 * 
	 * @return
	 */
	public long getGalleryId(Map<String, Object> paramMap) {
		Validate.notNull(paramMap.get("scenicId"), ErrorCode.ERROR_51001);

		// 没有相册类型，就默认取封面相册
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("scenicId", paramMap.get("scenicId"));
        if (paramMap.get("category") == null) {
            params.put("category", "cover");
        } else {
            params.put("category", paramMap.get("category"));

        }

        // 拿到Gallery的id
		List<Gallery> galleryList = list(params);

		if(galleryList.size() == 0){
			return 0;
		}
		
		return galleryList.get(0).getId();
	}
	
	/**
	 * 根据scenicId，找到galleryId
	 * <ul>
	 * <li>必选：景点id{scenicId}</li>
	 * </ul>
	 * 
	 * @return
	 */
	public List<GalleryImage> getGallery(Map<String, Object> paramMap){
		if(paramMap.get("galleryId") == null){
			paramMap.put("galleryId", getGalleryId(paramMap));
		}
		if(paramMap.get("galleryId").toString().equals("0")){
			return new ArrayList<GalleryImage>();
		}
		return getGalleryImage(paramMap);
	}
	
	/**
	 * 根据GalleryId，找到相册中所有相片
	 * <ul>
	 * <li>必选：相册id{galleryId}</li>
	 * <li>可选：分页大小{pageSize=10}</li>
	 * <li>可选：请求页码{page=1}</li>
	 * </ul>
	 * 
	 * @return
	 */
	public List<GalleryImage> getGalleryImage(
			final Map<String, Object> paramMap) {
		Validate.notNull(paramMap.get("galleryId"), ErrorCode.ERROR_51001);
		return galleryImageMapper.list(paramMap);

	}
	
	public int countGalleryImgCount(Map<String, Object> paramMap)
	{
		if(paramMap.get("galleryId") == null){
			long galleryId = getGalleryId(paramMap);
			if(galleryId == 0)
				return 0;
			paramMap.put("galleryId", galleryId);
		}
		
		return galleryImageMapper.count(paramMap);
	}
	
}
