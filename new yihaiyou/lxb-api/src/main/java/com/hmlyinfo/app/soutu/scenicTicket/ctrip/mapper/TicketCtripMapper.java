package com.hmlyinfo.app.soutu.scenicTicket.ctrip.mapper;

import java.util.List;
import java.util.Map;

import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.app.soutu.scenicTicket.ctrip.domain.TicketCtrip;

public interface TicketCtripMapper <T extends TicketCtrip> extends BaseMapper<T>{
	public List<TicketCtrip> listColumns(Map<String, Object> params);
	public TicketCtrip selByResourceId(int resourceId);
}
