package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.entity.Label;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONArray;

import javax.annotation.Resource;

/**
 * Created by zzl on 2016/1/3.
 */
public class LabelsWebAction extends JxmallAction {

    @Resource
    private LabelService labelService;

    public Result getRecplanListLabels() {
        Long parentId = Long.parseLong(getRequest().getParameter("parentId"));
        Label label = new Label();
        Label parentLabel = new Label();
        parentLabel.setId(parentId);
        label.setParent(parentLabel);
        return json(JSONArray.fromObject(labelService.list(label, null), JsonFilter.getIncludeConfig()));
    }

}
