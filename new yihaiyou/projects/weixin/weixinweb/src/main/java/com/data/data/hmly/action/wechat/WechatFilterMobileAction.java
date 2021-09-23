package com.data.data.hmly.action.wechat;

import com.data.data.hmly.action.FrameBaseAction;
import com.gson.WeChat;
import com.gson.util.Tools;
import com.opensymphony.xwork2.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Kingsley
 *         微信处理统一入口
 */
public class WechatFilterMobileAction extends FrameBaseAction {

    /**
     *
     */
    private static final long serialVersionUID = 4202253350042518763L;

    private static final Log LOGGER = LogFactory.getLog(WechatFilterMobileAction.class);

    public Result doFilter() throws IOException {
        HttpServletRequest request = getRequest();
        HttpServletResponse response = getResponse();
        Boolean isGet = "GET".equals(request.getMethod());
        if (isGet) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
        return null;
    }

    private void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/xml");
        ServletInputStream in = request.getInputStream();
        String xmlMsg = Tools.inputStream2String(in);
        LOGGER.debug("输入消息:[" + xmlMsg + "]");
        String xml = WeChat.processing(xmlMsg);
        response.getWriter().write(xml);
    }

    private void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getServletPath();
        String token = path.substring(path.indexOf("/") + 1);
        if (token.indexOf("/") >= 0) {
            token = token.substring(0, token.indexOf("/"));
        }
        String outPut = "error";
        if (token != null) {
            String signature = request.getParameter("signature"); // 微信加密签名
            String timestamp = request.getParameter("timestamp"); // 时间戳
            String nonce = request.getParameter("nonce"); // 随机数
            String echostr = request.getParameter("echostr"); //
            // 验证
            if (WeChat.checkSignature(token, signature, timestamp, nonce)) {
                outPut = echostr;
            }
        }
        response.getWriter().write(outPut);
    }

}
