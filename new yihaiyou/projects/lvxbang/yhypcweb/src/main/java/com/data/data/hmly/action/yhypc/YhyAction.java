package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.service.entity.Member;
import com.zuipin.action.JxmallAction;

/**
 * Created by huangpeijie on 2017-01-04,0004.
 */
public class YhyAction extends JxmallAction {
    public Member getLoginUser() {
        return (Member) getSession().getAttribute("LOGIN_USER");
    }
}
