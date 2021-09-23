package com.data.data.hmly.action.line;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.line.LineContactService;
import com.data.data.hmly.service.line.entity.LineContact;
import com.opensymphony.xwork2.Result;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/6/21.
 */
public class LineContactAction extends FrameBaseAction {

    @Resource
    private LineContactService lineContactService;

    private LineContact lineContact = new LineContact();

    private Map<String, Object> map = new HashMap<String, Object>();




    public Result saveContact() {

        if (lineContact.getId() == null) {
            lineContactService.saveLineContact(lineContact);
            if (lineContact.getId() != null) {
                simpleResult(map, true, "");
            } else {
                simpleResult(map, false, "");
            }
        }
        return jsonResult(map);
    }

    public Result getLineContactList() {

        List<LineContact> list = new ArrayList<LineContact>();

        if (lineContact.getLine().getId() != null) {
            list = lineContactService.getLineContactList(lineContact);
            return datagrid(list);
        }
        return datagrid(list);
    }

    public Result delContact() {
        if (lineContact.getId() != null) {
            lineContactService.delContact(lineContact);
            simpleResult(map, true, "");
        }
        return jsonResult(map);
    }


    public LineContact getLineContact() {
        return lineContact;
    }

    public void setLineContact(LineContact lineContact) {
        this.lineContact = lineContact;
    }
}
