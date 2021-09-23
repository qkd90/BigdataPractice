package com.data.data.hmly.action.external;

import com.data.data.hmly.action.FrameBaseAction;
import com.framework.struts.NotNeedLogin;
import com.opensymphony.xwork2.Result;

/**
 * Created by caiys on 2016/3/17.
 */
public class ExturlAction extends FrameBaseAction {

    /**
     * 供思明政府app链接
     */
    @NotNeedLogin
    public Result smzf() {
        return dispatch();
//        return redirect("http://wx.dzq.com/index.php?g=Wap&m=Index&a=index&token=bnkhet1437724216&wecha_id=oeJFFv0xSDXezSuVKxPlW_bSDYxg");
    }
}
