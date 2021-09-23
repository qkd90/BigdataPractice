package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.enums.LabelStatus;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.lxbcommon.CustomRequireService;
import com.data.data.hmly.service.lxbcommon.entity.CustomRequire;
import com.data.data.hmly.service.lxbcommon.entity.CustomRequireDestination;
import com.data.data.hmly.service.lxbcommon.entity.enums.CustomArrange;
import com.data.data.hmly.service.lxbcommon.entity.enums.CustomStatus;
import com.data.data.hmly.service.lxbcommon.entity.enums.CustomType;
import com.data.data.hmly.service.lxbcommon.entity.vo.CustomRequireDestinationVo;
import com.data.data.hmly.service.lxbcommon.entity.vo.CustomRequireVo;
import com.data.data.hmly.service.user.util.SmsUtil;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.MD5;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caiys on 2016/6/15.
 * 定制需求
 */
public class CustomRequireWebAction extends LxbAction {
    @Resource
    private CustomRequireService customRequireService;
    @Resource
    private LabelService labelService;
    @Resource
    private AreaService areaService;
    @Resource
    private MemberService memberService;

    // 页面字段
    private List<Map<String, List<Object>>> letterSortAreas;
    private List<TbArea> destinations;
    private String customType;

    private int pageNo;
    private int pageSize;

    /**
     * 定制需求填写页面
     * @return
     */
    public Result fill() {
        destinations = areaService.getHomeHotArea();
        Label label = new Label();
        label.setName("通用目的地-国内");
        label.setStatus(LabelStatus.USE);
        List<Label> labels = labelService.list(label, null);
        if (!labels.isEmpty()) {
            List<TbArea> sortAreas = areaService.getTrafficAreas(labels.get(0).getId());
            List<Map<String, Object>> sortMap = sortAreasList(sortAreas);
            letterSortAreas = letterSortAreasList(sortMap);
        }
        customType = (String) getParameter("customType");
        return dispatch();
    }

    /**
     * 发送短信
     * @return
     */
    public Result sendSms() {
        String phone = (String) getParameter("phone");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);
        if (StringUtils.isNotBlank(phone)) {
            try {
                SmsUtil.sendCustomRequireMessage(phone);
                map.put("success", true);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("errMsg", "手机验证码获取失败");
            }
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }

    /**
     * 保存定制需求信息
     * @return
     */
    public Result saveInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", false);

        // 验证码校验
        String code = (String) getParameter("code");
        Integer codeObj = (Integer) getRequest().getSession().getAttribute(SmsUtil.SMS_CODE_KEY);
        if (codeObj == null || !codeObj.toString().equals(code)) {
            map.put("errMsg", "验证码错误或已失效");
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }

        // 检查用户是否已经登录，未登录则自动登录，未存在用户自动注册并登录
        String contactPhone = (String) getParameter("contactPhone");
        Member member = getLoginUser(false);
        if (member == null) {
            SysSite sysSite = new SysSite();
            sysSite.setId(-1L);
            member = memberService.findByAccount(contactPhone, sysSite);
            if (member == null) {
                String contactor = (String) getParameter("contactor");
                String contactEmail = (String) getParameter("contactEmail");
                member = new Member();
                member.setAccount(contactPhone);
                member.setMobile(contactPhone);
                member.setNickName(contactor);
                member.setUserName(contactor);
                String pwd = SmsUtil.genCode().toString();
                member.setPassword(MD5.caiBeiMD5(pwd));
                member.setStatus(UserStatus.activity);
                member.setSysSite(sysSite);
                member.setTelephone(contactPhone);
                member.setBalance(0D);
                member.setEmail(contactEmail);
                memberService.save(member);
//                if (!result) {
//                    map.put("success", false);
//                    map.put("errMsg", "注册失败");
//                    JSONObject jsonObject = JSONObject.fromObject(map);
//                    return json(jsonObject);
//                }
                SmsUtil.sendAutoRegister(contactPhone, contactPhone, pwd);
            }
            getSession().setAttribute(UserConstans.CURRENT_LOGIN_USER, member);
            getSession().setAttribute("account", member.getAccount());
            getSession().setAttribute("staffName", member.getUserName());
            getSession().setAttribute("userName", member.getUserName());
        }

        // 参数处理
        CustomRequire customRequire = buildData();
        customRequireService.saveCustomRequire(customRequire);
        getRequest().getSession().removeAttribute(SmsUtil.SMS_CODE_KEY);
        SmsUtil.sendCustomSuccess(contactPhone);
        map.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }

    /**
     * 分页显示行程列表
     *
     * @return
     * @author caiys
     * @date 2015年12月22日 下午5:34:10
     */
    public Result page() {
        Page pageInfo = new Page(pageNo, pageSize);

        CustomRequire condition = new CustomRequire();
        Member member = new Member();
        member.setId(getLoginUser().getId());
        condition.setMember(member);
        List<CustomRequire> list = customRequireService.getCustomReqList(condition, pageInfo, "createTime", "desc");
        List<CustomRequireVo> result = new ArrayList<CustomRequireVo>();
        for (CustomRequire c : list) {
            CustomRequireVo vo = buildCustomRequireVo(c);
            result.add(vo);
        }
        pageInfo.setData(result);
        JSONObject json = JSONObject.fromObject(pageInfo);
        return json(json);
    }

    /**
     * 页面参数处理
     * @return
     */
    private CustomRequire buildData() {
        // 页面参数
        String customType = (String) getParameter("customType");
        String startCityId = (String) getParameter("startCityId");
        String destions = (String) getParameter("destions");
        String day = (String) getParameter("day");
        String startDate = (String) getParameter("startDate");
        String adjustFlag = (String) getParameter("adjustFlag");
        String arrange = (String) getParameter("arrange");
        String adult = (String) getParameter("adult");
        String child = (String) getParameter("child");
        String minPrice = (String) getParameter("minPrice");
        String maxPrice = (String) getParameter("maxPrice");
        String contactor = (String) getParameter("contactor");
        String contactPhone = (String) getParameter("contactPhone");
        String contactEmail = (String) getParameter("contactEmail");

        CustomRequire customRequire = new CustomRequire();
        customRequire.setCustomType(CustomType.valueOf(customType));
        TbArea area = new TbArea();
        area.setId(Long.valueOf(startCityId));
        customRequire.setStartCity(area);
        customRequire.setDay(Integer.valueOf(day));
        customRequire.setStartDate(DateUtils.getDate(startDate, "yyyy-MM-dd"));
        customRequire.setAdjustFlag(Boolean.valueOf(adjustFlag));
        customRequire.setArrange(CustomArrange.valueOf(arrange));
        customRequire.setAdult(Integer.valueOf(adult));
        if (StringUtils.isNotBlank(child) && Integer.parseInt(child) > 0) {
            customRequire.setChild(Integer.valueOf(child));
        }
        if (StringUtils.isNotBlank(minPrice)) {
            customRequire.setMinPrice(Float.valueOf(minPrice));
        }
        if (StringUtils.isNotBlank(maxPrice)) {
            customRequire.setMaxPrice(Float.valueOf(maxPrice));
        }
        customRequire.setContactor(contactor);
        customRequire.setContactPhone(contactPhone);
        customRequire.setContactEmail(contactEmail);
        String[] destionArray = destions.split(",");
        List<CustomRequireDestination> list = new ArrayList<CustomRequireDestination>();
        for (String d : destionArray) {
            CustomRequireDestination crd = new CustomRequireDestination();
            crd.setCustomRequire(customRequire);
            crd.setCreateTime(new Date());
            TbArea a = areaService.get(Long.valueOf(d));
            crd.setCity(a);
            crd.setCityName(a.getName());
            crd.setLevel(a.getLevel());
            list.add(crd);
        }
        customRequire.setDestinations(list);
        customRequire.setMember(getLoginUser());
        customRequire.setCreateTime(new Date());
        customRequire.setStatus(CustomStatus.handling);
        return customRequire;
    }

    /**
     * 转为页面对象
     * @param c
     * @return
     */
    private CustomRequireVo buildCustomRequireVo(CustomRequire c) {
        CustomRequireVo vo = new CustomRequireVo();
        vo.setId(c.getId());
        vo.setStartCityId(c.getStartCity().getId());
        vo.setStartCityName(c.getStartCity().getName());
        vo.setDay(c.getDay());
        vo.setAdult(c.getAdult());
        if (c.getChild() != null) {
            vo.setChild(c.getChild());
        }
        vo.setCustomType(c.getCustomType());
        vo.setCustomTypeName(c.getCustomType().getDescription());
        vo.setMinPrice(c.getMinPrice());
        vo.setMaxPrice(c.getMaxPrice());
        if (c.getMinPrice() == null && c.getMaxPrice() != null) {
            vo.setBudget(c.getMaxPrice().intValue() + "元以下");
        } else if (c.getMinPrice() != null && c.getMaxPrice() == null) {
            vo.setBudget(c.getMinPrice().intValue() + "元以上");
        } else if (c.getMinPrice() == null && c.getMaxPrice() == null) {
            vo.setBudget("不确定");
        } else {
            vo.setBudget(c.getMinPrice().intValue() + "-" + c.getMaxPrice().intValue() + "元");
        }
        vo.setStatus(c.getStatus());
        vo.setStatusName(c.getStatus().getDescription());
        vo.setCreateTime(DateUtils.format(c.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        if (c.getCustomRequireDestinations() != null) {
            List<CustomRequireDestinationVo> customRequireDestinationVos = new ArrayList<CustomRequireDestinationVo>();
            for (CustomRequireDestination d : c.getCustomRequireDestinations()) {
                CustomRequireDestinationVo dVo = new CustomRequireDestinationVo();
                dVo.setId(d.getId());
                dVo.setCityId(d.getCity().getId());
                dVo.setCityName(d.getCityName());
                dVo.setLevel(d.getLevel());
                customRequireDestinationVos.add(dVo);
            }
            vo.setCustomRequireDestinationVos(customRequireDestinationVos);
        }
        return vo;
    }


    public List<Map<String, Object>> sortAreasList(List<TbArea> sortAreas) {

        Map<String, List<TbArea>> map = Maps.newHashMap();
        for (TbArea a : sortAreas) {
            if (a.getPinyin() != null) {
                String first = a.getPinyin().substring(0, 1).toUpperCase();
                List<TbArea> list = map.get(first);
                if (list == null) {
                    list = new ArrayList();
                }
                list.add(a);
                map.put(first, list);

            }
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Map.Entry<String, List<TbArea>> entry : map.entrySet()) {
            Map<String, Object> temp = Maps.newHashMap();
            temp.put("name", entry.getKey());
            temp.put("list", entry.getValue());
            result.add(temp);
        }
        Collections.sort(result, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                char a = o1.get("name").toString().charAt(0);
                char b = o2.get("name").toString().charAt(0);
                return a - b;
            }
        });
        return result;
    }

    /**
     * 城市字母分类
     * @param sortMap
     * @return
     */
    public List<Map<String, List<Object>>> letterSortAreasList(List<Map<String, Object>> sortMap) {
        List<Map<String, List<Object>>> result = new ArrayList<Map<String, List<Object>>>();
        String[] letterRanges = new String[]{"A-E", "F-J", "K-P", "Q-W", "X-Z"};
        for (int i = 0; i < letterRanges.length; i++) {
            Map<String, List<Object>> rangeMap = Maps.newHashMap();
            rangeMap.put("letterRange", new ArrayList<Object>());
            result.add(rangeMap);
        }
        for (Map<String, Object> map : sortMap) {
            for (int i = 0; i < letterRanges.length; i++) {
                String letterRange = letterRanges[i];
                String[] letters = letterRange.split("-");
                if (letters[0].compareTo((String) map.get("name")) <= 0 && letters[1].compareTo((String) map.get("name")) >= 0) {
                    result.get(i).get("letterRange").add(map);
                }
            }
        }
        return result;
    }

    public List<Map<String, List<Object>>> getLetterSortAreas() {
        return letterSortAreas;
    }

    public void setLetterSortAreas(List<Map<String, List<Object>>> letterSortAreas) {
        this.letterSortAreas = letterSortAreas;
    }

    public List<TbArea> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<TbArea> destinations) {
        this.destinations = destinations;
    }

    public String getCustomType() {
        return customType;
    }

    public void setCustomType(String customType) {
        this.customType = customType;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
