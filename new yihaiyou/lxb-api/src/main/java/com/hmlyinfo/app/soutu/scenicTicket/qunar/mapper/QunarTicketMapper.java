package com.hmlyinfo.app.soutu.scenicTicket.qunar.mapper;

import java.util.List;
import com.hmlyinfo.app.soutu.scenicTicket.qunar.domain.QunarTicket;
import com.hmlyinfo.base.persistent.BaseMapper;

public interface QunarTicketMapper <T extends QunarTicket> extends BaseMapper<T>{
	
	List<QunarTicket> listSeasonTicket();
}
