package com.hmlyinfo.app.soutu.bargain.mapper;

import com.hmlyinfo.app.soutu.bargain.domain.BargainPlanOrder;
import com.hmlyinfo.base.persistent.BaseMapper;

public interface BargainPlanOrderMapper<T extends BargainPlanOrder> extends BaseMapper<T> {
    public void delete(Long id);
}
