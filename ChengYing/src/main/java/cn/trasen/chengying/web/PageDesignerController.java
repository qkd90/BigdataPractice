package cn.trasen.chengying.web;

import cn.trasen.chengying.entity.ResultParameters;
import cn.trasen.chengying.entity.pagedesigner.*;
import cn.trasen.chengying.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static cn.trasen.chengying.service.CurrentTime.currentTime;

/**
 * @author rq
 */
@Slf4j
@RestController
@RequestMapping(value = "/chengying.trasen.cn/designer/v1/storage/view")
public class PageDesignerController {
    @Autowired
    private Pages pages;
    @Autowired
    private Panels panels;
    @Autowired
    private Components components;
    @Autowired
    private Elements elements;
    @Autowired
    private Events events;

    /**页面
     * 查询页面
     */
    @GetMapping(value = "/page")
    public ResultParameters<Object> findPage (@RequestBody @Valid InputUpdate request)
            throws CommonUnknownException {
        ReturnFindPage returnFindPage = pages.findPage(request);
        log.info("查询结果 str={}",returnFindPage);
        if (returnFindPage ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultPageDesigner = new ResultParameters<>();
        resultPageDesigner.setCode(1);
        resultPageDesigner.setMessage("true");
        resultPageDesigner.setTime(currentTime());
        resultPageDesigner.setData(returnFindPage);
        return resultPageDesigner;
    }
    /**
     * 新增页面
     */
    @PostMapping(value = "/page")
    public ResultParameters<Object> writePage (@RequestBody @Valid InputWritePage request)
            throws CommonUnknownException {
        String str = pages.writePage(request);
        log.info("返回id： str={}",str);
        if (str ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(str);
        return resultParameters;
    }
    /**
     * 更改页面
     */
    @PatchMapping (value = "/panel")
    public ResultParameters<Object> writePage (@RequestBody @Valid InputUpdate request)
            throws CommonUnknownException {
        String str = pages.updatePage(request);
        log.info("修改结果： str={}",str);
        if (str ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(str);
        return resultParameters;
    }

    /**面板
     * 新增面板
     */
    @PostMapping (value = "/panels")
    public ResultParameters<Object> createPanels (@RequestBody @Valid InputCreate request)
            throws CommonUnknownException {
        String str = panels.createPanels(request);
        log.info("新增面板id： str={}",str);
        if (str ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(str);
        return resultParameters;
    }

    /**
     * 查询面板
     */
    @GetMapping (value = "/panels")
    public ResultParameters<Object> readPanels (@RequestBody @Valid InputOnlyId request)
            throws CommonUnknownException {
        ReturnPanels returnPanels = panels.readPanels(request);
        log.info("查询面板结果： str={}",returnPanels);
        if (returnPanels ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(returnPanels);
        return resultParameters;
    }

    /**
     * 更改面板
     */
    @PatchMapping (value = "/panels")
    public ResultParameters<Object> updatePanels (@RequestBody @Valid InputUpdate request)
            throws CommonUnknownException {
        String str = panels.updatePanels(request);
        log.info("更新面板id： str={}",str);
        if (str ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(str);
        return resultParameters;
    }

    /**复合组件
     * 新增复合组件
     */
    @PostMapping (value = "/components/")
    public ResultParameters<Object> createComponents (@RequestBody @Valid InputCreate request)
            throws CommonUnknownException {
        String str = components.createComponents(request);
        log.info("新增复合组件id： str={}",str);
        if (str ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(str);
        return resultParameters;
    }

    /**
     * 查询复合组件
     */
    @GetMapping (value = "/components/")
    public ResultParameters<Object> readComponents (@RequestBody @Valid InputOnlyId request)
            throws CommonUnknownException {
        ReturnPanels returnPanels = components.readComponents(request);
        log.info("查询复合组件结果： str={}",returnPanels);
        if (returnPanels ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(returnPanels);
        return resultParameters;
    }

    /**
     * 更改复合组件
     */
    @PatchMapping (value = "/components")
    public ResultParameters<Object> updateComponents (@RequestBody @Valid InputUpdate request)
            throws CommonUnknownException {
        String str = components.updateComponents(request);
        log.info("更新复合组件id： str={}",str);
        if (str ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(str);
        return resultParameters;
    }

    /**
     * 删除复合组件
     */
    @DeleteMapping (value = "/components")
    public ResultParameters<Object> deleteComponents (@RequestBody @Valid InputUpdate request)
            throws CommonUnknownException {
        String str = components.deleteComponents(request);
        log.info("删除复合组件id： str={}",str);
        if (str ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(str);
        return resultParameters;
    }
    /**
     * 查询复合组件清单
     */
    @GetMapping (value = "/components_list/")
    public ResultParameters<Object> readComponentsList ()
            throws CommonUnknownException {
        List<ReturnList> returnList = components.readComponentsList();
        log.info("查询复合组件清单： str={}",returnList);
        if (returnList ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(returnList);
        return resultParameters;
    }
    

    /**基础组件
     * 新增基础组件
     */
    @PostMapping (value = "/elements/")
    public ResultParameters<Object> createElements (@RequestBody @Valid InputCreate request)
            throws CommonUnknownException {
        String str = elements.createElements(request);
        log.info("新增基础组件id： str={}",str);
        if (str ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(str);
        return resultParameters;
    }
    /**
     * 查询基础组件
     */
    @GetMapping (value = "/elements/")
    public ResultParameters<Object> readElements (@RequestBody @Valid InputOnlyId request)
            throws CommonUnknownException {
        ReturnElements returnPanels = elements.readElements(request);
        log.info("查询基础组件结果： str={}",returnPanels);
        if (returnPanels ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(returnPanels);
        return resultParameters;
    }

    /**
     * 更改基础组件
     */
    @PatchMapping (value = "/elements")
    public ResultParameters<Object> updateElements (@RequestBody @Valid InputUpdate request)
            throws CommonUnknownException {
        String str = elements.updateElements(request);
        log.info("更新基础组件id： str={}",str);
        if (str ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(str);
        return resultParameters;
    }

    /**
     * 删除基础组件
     */
    @DeleteMapping (value = "/elements")
    public ResultParameters<Object> deleteElements (@RequestBody @Valid InputUpdate request)
            throws CommonUnknownException {
        String str = elements.deleteElements(request);
        log.info("删除基础组件id： str={}",str);
        if (str ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(str);
        return resultParameters;
    }
    /**
     * 查询基础组件清单
     */
    @GetMapping (value = "/elements_list/")
    public ResultParameters<Object> readElementsList ()
            throws CommonUnknownException {
        List<ReturnList> returnList = elements.readElementsList();
        log.info("查询基础组件清单： str={}",returnList);
        if (returnList ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(returnList);
        return resultParameters;
    }

    /**事件
     * 新增事件
     */
    @PostMapping (value = "/events/")
    public ResultParameters<Object> createEvents (@RequestBody @Valid InputEvents request)
            throws CommonUnknownException {
        EventsDTO eventsDTO = events.createEvents(request);
        log.info("新增事件id： str={}",eventsDTO);
        if (eventsDTO ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(eventsDTO);
        return resultParameters;
    }
    /**
     * 查询事件
     */
    @GetMapping (value = "/events/{id}/{version}.js")
    public String readEvents(@RequestBody @PathVariable String version,@PathVariable String id)
            throws CommonUnknownException {
        String str = events.readEvents(version,id);
        log.info("查询事件结果： str={}",str);
        if (str ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        return str;
    }

    /**
     * 更改事件
     */
    @PatchMapping (value = "/events")
    public ResultParameters<Object> updateEvents (@RequestBody @Valid InputUpdateEvents request)
            throws CommonUnknownException {
        String str = events.updateEvents(request);
        log.info("更新事件id： str={}",str);
        if (str ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(str);
        return resultParameters;
    }

    /**
     * 删除事件
     */
    @DeleteMapping (value = "/events")
    public ResultParameters<Object> deleteEvents (@RequestBody @Valid InputOnlyId request)
            throws CommonUnknownException {
        String str = events.deleteEvents(request);
        log.info("删除事件id： str={}",str);
        if (str ==null){
            throw new CommonUnknownException(CommonEnum.QueryService, 0
                    ,"查询数据库异常","数据库没有该数据",null);
        }
        ResultParameters<Object> resultParameters = new ResultParameters<>();
        resultParameters.setCode(1);
        resultParameters.setMessage("true");
        resultParameters.setTime(currentTime());
        resultParameters.setData(str);
        return resultParameters;
    }
}