package com.hmlyinfo.app.soutu.bargain.service;

import com.hmlyinfo.app.soutu.bargain.domain.BargainPlanGallery;
import com.hmlyinfo.app.soutu.bargain.mapper.BargainPlanGalleryMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BargainPlanGalleryService extends BaseService<BargainPlanGallery, Long> {

    @Autowired
    private BargainPlanGalleryMapper<BargainPlanGallery> mapper;

    @Override
    public BaseMapper<BargainPlanGallery> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

}
