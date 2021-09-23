package com.hmlyinfo.app.soutu.scenicTicket.ctrip.mapper;

import com.hmlyinfo.app.soutu.scenicTicket.ctrip.domain.CtripUser;
import com.hmlyinfo.base.persistent.BaseMapper;


public interface CtripUserMapper <T extends CtripUser> extends BaseMapper<T>{
	public CtripUser selByUid(int uid);
}


