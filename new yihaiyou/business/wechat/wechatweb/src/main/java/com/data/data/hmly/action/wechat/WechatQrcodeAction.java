package com.data.data.hmly.action.wechat;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.WechatQrcodeService;
import com.data.data.hmly.service.wechat.WechatService;
import com.data.data.hmly.service.wechat.entity.WechatQrcode;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vacuity on 15/11/26.
 */
public class WechatQrcodeAction extends FrameBaseAction {


    @Resource
    private WechatQrcodeService wechatQrcodeService;
    @Resource
    private WechatService wechatService;
    @Resource
    private WechatAccountService wechatAccountService;

    private int page;
    private int rows;

    private Long id;
    private Long accountId;
    private String sceneStr;
    private String name;

    private Map<String, Object> map = new HashMap<String, Object>();


    public Result statisticsManage() {
        return dispatch();
    }

    public Result list() {
        return dispatch();
    }

    public Result validateSceneStr() {

        String qrcode = (String) getParameter("qrcode");

        WechatQrcode wechatQrcode = null;
        if (StringUtils.isNotBlank(qrcode)) {
            wechatQrcode = wechatQrcodeService.get(Long.parseLong(qrcode));
        }
        WechatQrcode qrcodes = wechatQrcodeService.findQrcodeByAccountAndSceneStr(accountId, sceneStr);

        if (qrcodes == null) {
            map.put("notExisted", true);
        } else {
            if (wechatQrcode == qrcodes) {
                map.put("notExisted", true);
            } else {
                map.put("notExisted", false);
            }
        }
        simpleResult(map, true, null);
        return jsonResult(map);
    }

    public Result getQrcodeList() {


        List<WechatQrcode> wechatQrcodeList = wechatQrcodeService.getList(accountId, null);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(new Class[]{}, "wechatAccount");
        return datagrid(wechatQrcodeList, jsonConfig);
    }

    public Result getList() {

        Page pageInfo = new Page(page, rows);

        List<WechatQrcode> wechatQrcodeList = wechatQrcodeService.getList(accountId, pageInfo);
//        Map<String, Object> result = new HashMap<String, Object>();
//        result.put("qrcodes", wechatQrcodeList);
//        return jsonResult(result);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(new Class[]{}, "wechatAccount");

        return datagrid(wechatQrcodeList, pageInfo.getTotalCount(), jsonConfig);
    }


    @AjaxCheck
    public Result doMakeQrcode() {
        sceneStr = URLDecoder.decode(sceneStr);
        name = URLDecoder.decode(name);
        String qrcodePath = null;
        try {
            qrcodePath = wechatService.doCreateLimitQrcodeUrl(accountId, sceneStr);
        } catch (Exception e) {
            simpleResult(map, false, "生成二维码错误");
            return jsonResult(map);
        }
        if (qrcodePath == null) {
            simpleResult(map, false, "生成二维码错误");
            return jsonResult(map);
        }

        WechatQrcode wechatQrcode = new WechatQrcode();
        if (id != null && id != 0) {
            wechatQrcode = wechatQrcodeService.get(id);
        }
        wechatQrcode.setWechatAccount(wechatAccountService.get(accountId));
        wechatQrcode.setName(name);
        wechatQrcode.setPath(qrcodePath);
        wechatQrcode.setSceneStr(sceneStr);
        wechatQrcode.setCreateTime(new Date());
        wechatQrcode.setUser(getLoginUser());
        wechatQrcodeService.save(wechatQrcode);

        simpleResult(map, true, "");
        return jsonResult(map);
    }

    public Result delWechatQrcode() {
        wechatQrcodeService.del(id);

        WechatQrcode wechatQrcode = wechatQrcodeService.get(id);

        if (wechatQrcode == null) {
            simpleResult(map, true, "删除成功！");
        } else {
            simpleResult(map, false, "删除失败");
        }
        return jsonResult(map);
    }

    public Result getDetail() {
        WechatQrcode wechatQrcode = wechatQrcodeService.get(id);
        Map<String, Object> detail = new HashMap<String, Object>();
        detail.put("id", id);
        detail.put("name", wechatQrcode.getName());
        detail.put("sceneStr", wechatQrcode.getSceneStr());
        return jsonResult(detail);
    }

    public String download() {
        HttpServletResponse response = getResponse();
        WechatQrcode qrcode = wechatQrcodeService.get(id);

        String remoteFileName = qrcode.getPath();
        String filedisplay = qrcode.getName() + ".jpg";
        OutputStream outp = null;
        DataInputStream in = null;
        response.reset(); //可以加也可以不加
        response.setContentType("application/x-download");
        //此方法只能用户HTTP协议
        try {
            filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
            URL url = new URL(remoteFileName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            in = new DataInputStream(connection.getInputStream());
            outp = response.getOutputStream();

            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) > 0) {
                outp.write(b, 0, i);
            }

            outp.flush();
            //要加以下两句话，否则会报错
//            java.lang.IllegalStateException: getOutputStream() has already been called for //this response
//            outp.clear();
//            outp = pageContext.pushBody();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
        }

        return null;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getSceneStr() {
        return sceneStr;
    }

    public void setSceneStr(String sceneStr) {
        this.sceneStr = sceneStr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
