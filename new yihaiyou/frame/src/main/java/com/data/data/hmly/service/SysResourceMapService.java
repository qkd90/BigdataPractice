package com.data.data.hmly.service;

import com.data.data.hmly.enums.ResourceMapType;
import com.data.data.hmly.service.dao.SysResourceMapDao;
import com.data.data.hmly.service.entity.SysResourceMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vacuity on 15/10/27.
 */

@Service
public class SysResourceMapService {

    @Resource
    private SysResourceMapDao sysResourceMapDao;

    public SysResourceMap get(long id){
        return sysResourceMapDao.get(id);
    }

	public List<SysResourceMap> getByDesc(String desc) {
		SysResourceMap condition = new SysResourceMap();
		condition.setDescription(desc);
		return sysResourceMapDao.getList(condition);
	}

    public List<SysResourceMap> getMapList(){
        return sysResourceMapDao.getList();
    }

	public List<SysResourceMap> getByType(ResourceMapType type) {
		SysResourceMap sysResourceMap = new SysResourceMap();
		sysResourceMap.setResourceType(type);
		return sysResourceMapDao.getList(sysResourceMap);
	}

	public List<SysResourceMap> find(SysResourceMap sysResourceMap) {
		return sysResourceMapDao.getList(sysResourceMap);
	}
}
