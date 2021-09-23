package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.action.FrameBaseAction;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.FileUtil;

/**
 * Created by dy on 2017/1/6.
 */
public class IndexWebAction extends FrameBaseAction {


    public Result index() {
        setAttribute(YhyConstants.YHY_INDEX_KEY, FileUtil.loadHTML(YhyConstants.YHY_INDEX));
        return dispatch();
    }

}
