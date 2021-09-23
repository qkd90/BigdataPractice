package com.data.data.hmly.service.zmyproduct;

import com.alibaba.fastjson.JSON;
import com.data.data.hmly.service.zmyproduct.dao.ZmyTicketDao;
import com.data.data.hmly.service.zmyproduct.entity.OrderDto;
import com.data.data.hmly.service.zmyproduct.entity.OrderProductDto;
import com.data.data.hmly.service.zmyproduct.entity.OrderResult;
import com.data.data.hmly.service.zmyproduct.entity.ZmyTicket;
import com.data.data.hmly.service.zmyproduct.utils.ZmyouApiUtils;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by dy on 2016/5/9.
 */
@Service
public class ZmyTicketService {

    private Log log = LogFactory.getLog(this.getClass());
    public static final String URL = "http://222.76.251.180:8380/ws/order/create.json"; //接口请求地址

    @Resource
    private ZmyTicketDao zmyTicketDao;

    private Map<String, Object> map = new HashMap<String, Object>();


    public void doCreateOrder(){


        OrderDto dto = new OrderDto();
        List<OrderProductDto> productList = new ArrayList<OrderProductDto>();

        dto.setDistributorId(204l);
        dto.setOutOrderNo("D20150430000001");
        dto.setTotal(1000f);
        dto.setBuyerName("陈龙毅");
        dto.setBuyerIdNo("123456");
        dto.setBuyerMobile("15980778880");

        for (int i = 0; i < 10; i++){
            OrderProductDto productDto = new OrderProductDto();
            productDto.setProductId(String.valueOf(i));
            productDto.setProductType("1");
            productDto.setAmount("1000");
            productDto.setVisitorName("陈龙毅");
            productDto.setVisitorIdNo("350204199103252014");
            productDto.setVisitorMobile("15980778880");
            productDto.setTicketState("1");
            productList.add(productDto);
        }

        String encodeStr = ZmyouApiUtils.encode(JSON.toJSONString(dto));

        JSONObject object = doPostOpenService(encodeStr);

    }

    /**
     * 功能描述：同步芝麻游门票数据
     * @param productListStr
     * @return
     */
    public Map<String, Object> doSynProduct(String productListStr) {
        List<ZmyTicket> zmyTicketList = new ArrayList<ZmyTicket>();
        List<ZmyTicket> saveTicketList = new ArrayList<ZmyTicket>();
        List<ZmyTicket> updateTicketList = new ArrayList<ZmyTicket>();
        JSONArray jsonArray = JSONArray.fromObject(productListStr);
        if (jsonArray.isEmpty()) {
            map.put("success", false);
            map.put("info", "数据为空");
            return map;
        }
        for (Object o : jsonArray) {
            JSONObject jsonObject = JSONObject.fromObject(o);
            ZmyTicket zmyTicket = (ZmyTicket) JSONObject.toBean(jsonObject, ZmyTicket.class);
            zmyTicketList.add(zmyTicket);
        }
        for (ZmyTicket temp : zmyTicketList) {
            ZmyTicket zmyTicket = findZmyTicketByProId(temp.getProductId());
            if (zmyTicket != null) {
                zmyTicket.setName(temp.getName());
                zmyTicket.setDistributorId(temp.getDistributorId());
                zmyTicket.setIsTeam(temp.getIsTeam());
                zmyTicket.setMarketPrice(temp.getMarketPrice());
                zmyTicket.setMaxNum(temp.getMaxNum());
                zmyTicket.setMinNum(temp.getMinNum());
                zmyTicket.setRealname(temp.getRealname());
                zmyTicket.setRefundFactorage(temp.getRefundFactorage());
                zmyTicket.setPurchasePrice(temp.getPurchasePrice());
                zmyTicket.setRefundTimeLag(temp.getRefundTimeLag());
                zmyTicket.setState(temp.getState());
                zmyTicket.setValidity(temp.getValidity());
                zmyTicket.setValidType(temp.getValidType());
                zmyTicket.setRefundType(temp.getRefundType());
                zmyTicket.setType(temp.getType());
                updateTicketList.add(zmyTicket);
            } else {
                saveTicketList.add(temp);
            }
        }
        zmyTicketDao.save(saveTicketList);
        zmyTicketDao.update(updateTicketList);
        map.put("success", true);
        return map;
    }

    private ZmyTicket findZmyTicketByProId(Long productId) {
        Criteria<ZmyTicket> criteria = new Criteria<ZmyTicket>(ZmyTicket.class);
        criteria.eq("productId", productId);
        return zmyTicketDao.findUniqueByCriteria(criteria);
    }


    public JSONObject doPostOpenService(String data) {

        JSONObject paramJson = new JSONObject();

        JSONObject resultJson = new JSONObject();

        OrderResult orderResult = new OrderResult();

//        map.put("data", orderResult.fmtResult(data));

//        paramJson = JSONObject.fromObject(map);
        paramJson = JSONObject.fromObject(orderResult.fmtResult(data));

        String paramStr = paramJson.toString().replaceAll("\\\\/", "\\/");  // JSONObject斜杠转义问题

        String resultStr = null;
        try {
            resultStr = postHttp(URL, paramStr);

            if (StringUtils.isNotBlank(resultStr)) {
                resultJson = JSONObject.fromObject(resultStr);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return resultJson;
    }

    public String postHttp(String url, String paramStr) throws UnsupportedEncodingException {
//        String postUrl = url + "?RequestJson=" + paramStr;
        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpRequst = new HttpPost(url);
        List nameValuePairs = new ArrayList();
        JSONObject jsonObject = JSONObject.fromObject(paramStr);
        Set set = jsonObject.keySet();
        Iterator its = set.iterator();
        while (its.hasNext()) {
            String key = (String) its.next();
            if ("head".equals(key)) {
                JSONObject headObject = (JSONObject) jsonObject.get(key);
                Set headSet = headObject.keySet();
                Iterator headIts = headSet.iterator();
                while (headIts.hasNext()) {
                    String headKey = (String) headIts.next();
                    nameValuePairs.add(new BasicNameValuePair(headKey, (String) headObject.get(headKey)));
                }
            } else {
                nameValuePairs.add(new BasicNameValuePair(key, (String) jsonObject.get(key)));
            }
        }
        httpRequst.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        try {
            //执行请求
            HttpResponse httpResponse = closeableHttpClient.execute(httpRequst);
            //获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            //判断响应实体是否为空
            if (entity != null) {
                String resultStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                log.info("返回结果：" + resultStr);
                return resultStr;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
