package cn.trasen.chengying.web;

import cn.trasen.chengying.entity.RequestServiceInfoQuery;
import cn.trasen.chengying.entity.ResultParameters;
import cn.trasen.chengying.entity.configureQuery.SQLAddressDTO;
import cn.trasen.chengying.entity.configureQuery.ServiceAddressDTO;
import cn.trasen.chengying.entity.dataList.InputReadList;
import cn.trasen.chengying.entity.dataList.ReturnSqlCommand;
import cn.trasen.chengying.entity.singleData.RequestGetFlowChart;
import cn.trasen.chengying.entity.singleData.RequestWriteGraph;
import cn.trasen.chengying.entity.singleData.ReturnGraph;
import cn.trasen.chengying.entity.singleData.ReturnGraphList;
import cn.trasen.chengying.service.*;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static cn.trasen.chengying.service.CurrentTime.currentTime;



/**
 * @author rq
 */
@Slf4j
@RestController
@RequestMapping(value = "/storage/flow/event")
public class ComposerController {
    @Autowired
    private ReadDocument readDocument;
    @Autowired
    private ServiceAddress serviceAddress;
    @Autowired
    private SqlAddress sqlAddress;
    @Autowired
    private GetFlowChart getFlowChart;
    @Autowired
    private WriteGraph writeGraph;
    @Autowired
    private  ReadList readList;
    @Autowired
    private  ReadGraphList readGraphList;


    /**配置查询
     * 服务地址查询
     */
    @PostMapping(value = "/service_address/read/")
    public ResultParameters<Object> queryServiceAddress() {
        ServiceAddressDTO strAddress = serviceAddress.findServiceAddress();
        log.info("查询结果 str={}",strAddress);
        ResultParameters<Object> resultsWeb = new ResultParameters<>();
        resultsWeb.setCode(1);
        resultsWeb.setMessage("true");
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(strAddress);
        return resultsWeb;
    }

    /**
     * SQL地址查询
     */
    @PostMapping(value = "/sql_service_address/read/")
    public ResultParameters<Object> sqlServiceAddress() {
        SQLAddressDTO strAddress = sqlAddress.findSqlAddress();
        log.info("查询结果 str={}",strAddress);
        ResultParameters<Object> resultsWeb = new ResultParameters<>();
        resultsWeb.setCode(1);
        resultsWeb.setMessage("true");
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(strAddress);
        return resultsWeb;
    }

    /**单个数据的报错和读取
     * 读取流程图
     */
    @PostMapping(value = "/graph/read/")
    public ResultParameters<Object> readGraph(@RequestBody @Valid RequestGetFlowChart request) {
        ReturnGraph returnGraph = getFlowChart.getFlowChart(request);
        log.info("查询结果 getFlowChartDTO={}",returnGraph);
        ResultParameters<Object> resultsWeb = new ResultParameters<>();
        resultsWeb.setCode(1);
        resultsWeb.setMessage("true");
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(returnGraph);
        return resultsWeb;
    }
    /**
     * 保存流程图
     */
    @PostMapping(value = "/graph/write/")
    public ResultParameters<Object> sqlServiceAddress(@RequestBody @Valid RequestWriteGraph request) {
        Integer codeNumber = writeGraph.writeGraph(request);
        ResultParameters<Object> resultsWeb = new ResultParameters<>();
        resultsWeb.setCode(codeNumber);
        resultsWeb.setMessage("true");
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
    public ResultParameters<Object> readGraphList() {
        List<ReturnGraphList> returnGraphList = readGraphList.readGraphList();
        log.info("查询结果");
        ResultParameters<Object> resultsWeb = new ResultParameters<>();
        resultsWeb.setCode(1);
        resultsWeb.setMessage("true");
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(returnGraphList);
        return resultsWeb;
    }

    /**
     * 获取SQL服务文档
     */
    @PostMapping(value = "/sql_service_document/read")
    public ResultParameters<Object> readSqlDocument(@RequestBody @Valid RequestServiceInfoQuery request) {
        String str = readDocument.readSql(request);
        log.info("查询结果 str={}",str);
        ResultParameters<Object> resultsWeb = new ResultParameters<>();
        resultsWeb.setCode(1);
        resultsWeb.setMessage("true");
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(JSONObject.parse(str));
        return resultsWeb;
    }
    /**
     * 获取微服务文档
     */
    @PostMapping(value = "/service_document/read/")
    public ResultParameters<Object> getByServiceName(@RequestBody @Valid RequestServiceInfoQuery request)
            throws CommonUnknownException {
        String str = readDocument.findByName(request);
        log.info("查询微服务结果 str={}",str);
        if (str ==null || str.isEmpty()){
            throw new CommonUnknownException(CommonEnum.QueryService, 0,"查询服务出入参异常","数据库没有该服务",null);
        }
        ResultParameters<Object> resultsWeb = new ResultParameters<>();
        resultsWeb.setCode(1);
        resultsWeb.setMessage("true");
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(JSONObject.parse(str));
        return resultsWeb;
    }

    /**获取可用数据清单
     * 获取SQL服务清单
     */
    @PostMapping(value = "/sql_service_list/read/")
    public ResultParameters<Object> readSqlList(@RequestBody @Valid InputReadList request) {
        ReturnSqlCommand returnSqlCommand = readList.readSqlList(request);
        log.info("查询sql服务清单结果 str={}",returnSqlCommand);
        ResultParameters<Object> resultsWeb = new ResultParameters<>();
        resultsWeb.setCode(1);
        resultsWeb.setMessage("true");
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(returnSqlCommand);
        return resultsWeb;
    }
    /**
      获取微服务清单
     */
    @PostMapping(value = "/service_list/read/")
    public ResultParameters<Object> readServiceList(@RequestBody @Valid InputReadList request) {
        String str = readList.readServiceList(request);
        log.info("查询微服务清单结果 str={}",str);
        ResultParameters<Object> resultsWeb = new ResultParameters<>();
        resultsWeb.setCode(1);
        resultsWeb.setMessage("true");
        resultsWeb.setTime(currentTime());
        resultsWeb.setData(JSONObject.parse(str));
        return resultsWeb;
    }
}