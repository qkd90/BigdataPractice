package com.example.chengying.web;

import com.alibaba.fastjson.JSONObject;
import com.example.chengying.entity.RequestServiceInfoQuery;
import com.example.chengying.entity.ResultWeb;
import com.example.chengying.entity.configureQuery.SQLAddressDTO;
import com.example.chengying.entity.configureQuery.ServiceAddressDTO;
import com.example.chengying.entity.dataList.InputReadList;
import com.example.chengying.entity.dataList.ReturnSqlCommand;
import com.example.chengying.entity.singleData.RequestGetFlowChart;
import com.example.chengying.entity.singleData.RequestWriteGraph;
import com.example.chengying.entity.singleData.ReturnGraph;
import com.example.chengying.entity.singleData.ReturnGraphList;
import com.example.chengying.service.dataList.ReadList;
import com.example.chengying.service.configureQuery.SQLAddress;
import com.example.chengying.service.configureQuery.ServiceAddress;
import com.example.chengying.service.globalexception.CommonEnum;
import com.example.chengying.service.globalexception.CommonUnknownException;
import com.example.chengying.service.singleData.GetFlowChart;
import com.example.chengying.service.singleData.ReadDocument;
import com.example.chengying.service.singleData.ReadGraphList;
import com.example.chengying.service.singleData.WriteGraph;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.example.chengying.service.CurrentTime.currentTime;


/**
 * @author rq
 */
@Slf4j
@RestController
@RequestMapping(value = "/storage/flow/event")
public class DemoController {
    private final ReadDocument readDocument;
    private final ServiceAddress serviceAddress;
    private final SQLAddress sqlAddress;
    private final GetFlowChart getFlowChart;
    private final WriteGraph writeGraph;
    private final ReadList readList;
    private final ReadGraphList readGraphList;

    public DemoController(ReadDocument readDocument, ServiceAddress serviceAddress
            , SQLAddress sqlAddress, GetFlowChart getFlowChart, WriteGraph writeGraph,
                          ReadList readList, ReadGraphList readGraphList) {
        this.readDocument = readDocument;
        this.serviceAddress = serviceAddress;
        this.sqlAddress = sqlAddress;
        this.getFlowChart = getFlowChart;
        this.writeGraph = writeGraph;
        this.readList = readList;
        this.readGraphList = readGraphList;
    }


    /**配置查询
     * 服务地址查询
     */
    @PostMapping(value = "/service_address/read/")
    public ResultWeb<Object> queryServiceAddress() {
        ServiceAddressDTO strAddress = serviceAddress.findServiceAddress();
        log.info("查询结果 str={}",strAddress);
        ResultWeb<Object> resultsWeb = new ResultWeb<>();
        resultsWeb.setCode(1);
        resultsWeb.setSuccess(true);
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(strAddress);
        return resultsWeb;
    }

    /**
     * SQL地址查询
     */
    @PostMapping(value = "/sql_service_address/read/")
    public ResultWeb<Object> sqlServiceAddress() {
        SQLAddressDTO strAddress = sqlAddress.findSQLAddress();
        log.info("查询结果 str={}",strAddress);
        ResultWeb<Object> resultsWeb = new ResultWeb<>();
        resultsWeb.setCode(1);
        resultsWeb.setSuccess(true);
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(strAddress);
        return resultsWeb;
    }

    /**单个数据的报错和读取
     * 读取流程图
     */
    @PostMapping(value = "/graph/read/")
    public ResultWeb<Object> readGraph(@RequestBody @Valid RequestGetFlowChart request) {
        ReturnGraph returnGraph = getFlowChart.getFlowChart(request);
        log.info("查询结果 getFlowChartDTO={}",returnGraph);
        ResultWeb<Object> resultsWeb = new ResultWeb<>();
        resultsWeb.setCode(1);
        resultsWeb.setSuccess(true);
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(returnGraph);
        return resultsWeb;
    }
    /**
     * 保存流程图
     */
    @PostMapping(value = "/graph/write/")
    public ResultWeb<Object> sqlServiceAddress(@RequestBody @Valid RequestWriteGraph request) {
        Integer codeNumber = writeGraph.writeGraph(request);
        ResultWeb<Object> resultsWeb = new ResultWeb<>();
        resultsWeb.setCode(codeNumber);
        resultsWeb.setSuccess(true);
        resultsWeb.setTime(currentTime());
        JSONObject msgResult = new JSONObject();
        msgResult.put("msg","保存成功！");
        resultsWeb.setData(msgResult);
        return resultsWeb;
    }

    /**
     * 获取流程图清单
     */
    @PostMapping(value = "/graph_list/read")
    public ResultWeb<Object> readGraphList() {
        List<ReturnGraphList> returnGraphList = readGraphList.readGraphList();
        log.info("查询结果");
        ResultWeb<Object> resultsWeb = new ResultWeb<>();
        resultsWeb.setCode(1);
        resultsWeb.setSuccess(true);
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(returnGraphList);
        return resultsWeb;
    }

    /**
     * 获取SQL服务文档
     */
    @PostMapping(value = "/sql_service_document/read")
    public ResultWeb<Object> readSqlDocument(@RequestBody @Valid RequestServiceInfoQuery request) {
        String str = readDocument.readSql(request);
        log.info("查询结果 str={}",str);
        ResultWeb<Object> resultsWeb = new ResultWeb<>();
        resultsWeb.setCode(1);
        resultsWeb.setSuccess(true);
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(JSONObject.parse(str));
        return resultsWeb;
    }
    /**
     * 获取微服务文档
     */
    @PostMapping(value = "/service_document/read/")
    public ResultWeb<Object> getByServiceName(@RequestBody @Valid RequestServiceInfoQuery request)
            throws CommonUnknownException {
        String str = readDocument.findByName(request);
        log.info("查询微服务结果 str={}",str);
        if (str ==null || str.isEmpty()){
            throw new CommonUnknownException(CommonEnum.QueryService, 0,"通用异常"
                    ,"查询服务出入参异常","数据库没有该服务",null);
        }
        ResultWeb<Object> resultsWeb = new ResultWeb<>();
        resultsWeb.setCode(1);
        resultsWeb.setSuccess(true);
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(JSONObject.parse(str));
        return resultsWeb;
    }

    /**获取可用数据清单
     * 获取SQL服务清单
     */
    @PostMapping(value = "/sql_service_list/read/")
    public ResultWeb<Object> readSqlList(@RequestBody @Valid InputReadList request) {
        ReturnSqlCommand returnSqlCommand = readList.readSqlList(request);
        log.info("查询sql服务清单结果 str={}",returnSqlCommand);
        ResultWeb<Object> resultsWeb = new ResultWeb<>();
        resultsWeb.setCode(1);
        resultsWeb.setSuccess(true);
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(returnSqlCommand);
        return resultsWeb;
    }
    /**
      获取微服务清单
     */
    @PostMapping(value = "/service_list/read/")
    public ResultWeb<Object> readServiceList(@RequestBody @Valid InputReadList request) {
        String str = readList.readServiceList(request);
        log.info("查询微服务清单结果 str={}",str);
        ResultWeb<Object> resultsWeb = new ResultWeb<>();
        resultsWeb.setCode(1);
        resultsWeb.setSuccess(true);
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(JSONObject.parse(str));
        return resultsWeb;
    }
}
