package com.hmlyinfo.app.soutu.scenic.service;

import com.hmlyinfo.app.soutu.scenic.domain.NoteScenic;
import com.hmlyinfo.app.soutu.scenic.mapper.NoteScenicMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by guoshijie on 2014/8/14.
 */
@Service
public class NoteScenicService extends BaseService<NoteScenic, Long> {

    @Autowired
    NoteScenicMapper noteScenicMapper;

    @Override
    public BaseMapper<NoteScenic> getMapper() {
        return noteScenicMapper;
    }

    @Override
    public String getKey() {
        return null;
    }
}
