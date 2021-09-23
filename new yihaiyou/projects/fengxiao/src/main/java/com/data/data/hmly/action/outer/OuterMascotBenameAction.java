package com.data.data.hmly.action.outer;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.outer.OuterMascotBenameService;
import com.data.data.hmly.service.outer.entity.OuterMascotBename;
import com.framework.struts.NotNeedLogin;
import com.opensymphony.xwork2.Result;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by caiys on 2017/1/6.
 */
@NotNeedLogin
public class OuterMascotBenameAction extends FrameBaseAction {
    @Resource
    private OuterMascotBenameService outerMascotBenameService;

    private OuterMascotBename outerMascotBename;
    private String errorMsg;
    private List<OuterMascotBename> mascots;

    /**
     * 1.欢迎页面
     * @return
     */
    public Result s1() {
        return dispatch();
    }

    /**
     * 2.一海游寓意
     * @return
     */
    public Result s2() {
        return dispatch();
    }

    /**
     * 3.吉祥物介绍
     * @return
     */
    public Result s3() {
        return dispatch();
    }

    /**
     * 4.奖品设置
     * @return
     */
    public Result s4() {
        return dispatch();
    }

    /**
     * 5.联系方式
     * @return
     */
    public Result s5() {
        return dispatch();
    }

    /**
     * 5.1 保存
     * @return
     */
    public Result save() {
        try {
            outerMascotBename.setCandidateFlag(false);
            outerMascotBename.setCreateTime(new Date());
            outerMascotBenameService.saveOuterMascotBename(outerMascotBename);
        } catch (Exception e) {
            errorMsg = "操作失败，请稍后重试！";
            e.printStackTrace();
            return dispatch("/WEB-INF/jsp/outer/outerMascotBename/s5.jsp");
        }
        return redirect("/outer/outerMascotBename/s6.jhtml");
    }

    /**
     * 6.结束页面
     * @return
     */
    public Result s6() {
        return dispatch();
    }

    /**
     * 列表显示页面
     * @return
     */
    public Result result() {
        mascots = outerMascotBenameService.list();
        return dispatch();
    }

    public OuterMascotBename getOuterMascotBename() {
        return outerMascotBename;
    }

    public void setOuterMascotBename(OuterMascotBename outerMascotBename) {
        this.outerMascotBename = outerMascotBename;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<OuterMascotBename> getMascots() {
        return mascots;
    }

    public void setMascots(List<OuterMascotBename> mascots) {
        this.mascots = mascots;
    }
}
