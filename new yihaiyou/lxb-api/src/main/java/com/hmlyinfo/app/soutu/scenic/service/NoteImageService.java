package com.hmlyinfo.app.soutu.scenic.service;

import com.hmlyinfo.app.soutu.scenic.domain.NoteImage;
import com.hmlyinfo.app.soutu.scenic.mapper.NoteImageMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by guoshijie on 2014/8/25.
 */
@Service
public class NoteImageService extends BaseService<NoteImage, Long>{

    @Autowired
    NoteImageMapper<NoteImage> imageMapper;

    @Override
    public BaseMapper<NoteImage> getMapper() {
        return imageMapper;
    }

    @Override
    public String getKey() {
        return null;
    }


}
