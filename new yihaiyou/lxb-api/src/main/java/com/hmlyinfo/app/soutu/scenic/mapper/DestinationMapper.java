package com.hmlyinfo.app.soutu.scenic.mapper;

import java.util.Map;

import com.hmlyinfo.app.soutu.scenic.domain.Destination;
import com.hmlyinfo.base.persistent.BaseMapper;

/**
 * Created by guoshijie on 2014/7/15.
 */
public interface DestinationMapper extends BaseMapper<Destination> {
	public Destination selByIds(Map<String, Object> paramMap);
}
