package com.hmlyinfo.app.soutu.scenic.mapper;

import com.hmlyinfo.app.soutu.scenic.domain.NoteImage;
import com.hmlyinfo.base.persistent.BaseMapper;

import java.util.Map;

public interface NoteImageMapper <T extends NoteImage> extends BaseMapper<T>{
    public void updateNum(Map<String, Object> params);
}
