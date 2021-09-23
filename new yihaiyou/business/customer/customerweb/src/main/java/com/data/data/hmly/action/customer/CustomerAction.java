package com.data.data.hmly.action.customer;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.customer.vo.MemberComboVo;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.customer.CustomerService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;
import com.zuipin.util.UUIDUtil;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by vacuity on 15/11/2.
 */
public class CustomerAction extends FrameBaseAction {


    @Resource
    private CustomerService customerService;
    @Resource
    private MemberService memberService;


    private int page;
    private int rows;
    private File avatar;
    private String birthday;
    private String orderProperty;
    private String orderType;
    private Long id;

    private Member member = new Member();

    private int orderNum;

    public Result list() {
        return dispatch();
    }

    /**
     * 老接口, 废弃
     * @return
     */
//    @Deprecated
//    public Result getList() {
//        User loginUser = getLoginUser();
//        Page pages = new Page(page, rows);
//        List<User> customerList = customerService.getCustomerList(loginUser, pages, account, mobile);
//        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(new Class[]{}, new String[]{"sysSite", "sysUnit"});
//        return datagrid(customerList, pages.getTotalCount(), jsonConfig);
//    }

    /**
     * 客户管理新接口
     * @return
     */
    public Result getMemberList() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Page page = new Page(this.page, this.rows);
        List<Member> memberList = memberService.list(member, page, orderProperty, orderType);
        for (Member member : memberList) {
            member.setAccount(StringUtils.htmlEncode(member.getAccount()));
            member.setUserName(StringUtils.htmlEncode(member.getUserName()));
            member.setNickName(StringUtils.htmlEncode(member.getNickName()));
            member.setEmail(StringUtils.htmlEncode(member.getEmail()));
        }
        return datagrid(memberList, page.getTotalCount());
    }

    /**
     * 获取客户简要信息(用户下拉列表 )
     * @return
     */
    public Result getMemberCoboData() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Page page = new Page(this.page, this.rows);
        List<Member> memberList = memberService.list(member, page, orderProperty, orderType);
        List<MemberComboVo> memberComboVoList = new ArrayList<MemberComboVo>();
        for (Member member : memberList) {
            MemberComboVo memberComboVo = new MemberComboVo();
            memberComboVo.setId(member.getId());
            memberComboVo.setUserName(member.getUserName());
            memberComboVo.setNickName(member.getNickName());
            if (StringUtils.hasText(member.getMobile())) {
                memberComboVo.setContact(member.getMobile());
            } else if (StringUtils.hasText(member.getTelephone())) {
                memberComboVo.setContact(member.getTelephone());
            } else if (StringUtils.hasText(member.getEmail())) {
                memberComboVo.setContact(member.getEmail());
            }
            memberComboVoList.add(memberComboVo);
        }
        return datagrid(memberComboVoList, page.getTotalCount());
    }


    /**
     * 老接口, 弃用
     * @return
     */
//    @Deprecated
//    public Result detail() {
//        member = memberService.get(id);
//        orderNum = customerService.getOrderNum(user);
//        Map<String, Object> detailMap = new HashMap<String, Object>();
//        detailMap.put("userType", user.getUserType());
//        detailMap.put("id", user.getId());
//        detailMap.put("account", user.getAccount());
//        detailMap.put("userName", user.getUserName());
//        detailMap.put("mobile", user.getMobile());
//        detailMap.put("email", user.getEmail());
//        detailMap.put("address", user.getAddress());
//        if (user.getUserType() == UserType.USER) {
//            Member member = (Member) user;
//            detailMap.put("hasMoney", member.getBalance());
//            detailMap.put("jifen", member.getJifen());
//        }
//        else {
//            SysUser sysUser = (SysUser) user;
//            detailMap.put("gender", sysUser.getGender());
//            detailMap.put("sysSiteName", sysUser.getSysSite().getSitename());
//            detailMap.put("sysUnitName", sysUser.getSysUnit().getName());
//        }

//        detailMap.put("orderNum", orderNum);
//
//        return jsonResult(detailMap);
//    }

    public Result memberDetail() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            member = memberService.get(id);
        }
        if (member != null && member.getId() > 0) {
            result.put("id", member.getId());
            result.put("member.id", member.getId());
            result.put("member.account", member.getAccount());
            result.put("member.userName", member.getUserName());
            result.put("member.createdTime", DateUtils.format(member.getCreatedTime(), "yyyy-MM-dd"));
            result.put("member.memberName", member.getUserName());
            result.put("member.nickName", member.getNickName());
            result.put("member.head", member.getHead());
            result.put("birthday", DateUtils.format(member.getBirthday(), "yyyy-MM-dd"));
            result.put("member.gender", member.getGender());
            result.put("member.jifen", member.getJifen());
            result.put("member.telephone", member.getTelephone());
            result.put("member.mobile", member.getMobile());
            result.put("member.email", member.getEmail());
            result.put("member.qqNo", member.getQqNo());
            result.put("member.address", member.getAddress());
            result.put("member.status", member.getStatus());
            result.put("member.balance", member.getBalance());
        }
        return json(JSONObject.fromObject(result));
    }



    public Result saveMember() {
        Map<String, Object> result = new HashMap<String, Object>();
        Member targetMember;
        if (id != null && id > 0) {
            targetMember = memberService.get(id);
        } else {
            targetMember = new Member();
            targetMember.setCreatedTime(new Date());
            targetMember.setHead("/userface/head.gif");
        }
        if (StringUtils.hasText(member.getUserName())) {
            targetMember.setUserName(StringUtils.htmlEncode(member.getUserName()));
        }
        if (StringUtils.hasText(member.getNickName())) {
            targetMember.setNickName(StringUtils.htmlEncode(member.getNickName()));
        }
        if (StringUtils.hasText(birthday)) {
            targetMember.setBirthday(DateUtils.getDate(birthday, "yyyy-MM-dd"));
        }
        if (member.getGender() != null) {
            targetMember.setGender(member.getGender());
        }
        if (StringUtils.hasText(member.getTelephone())) {
            targetMember.setTelephone(StringUtils.htmlEncode(member.getTelephone()));
        }
        if (StringUtils.hasText(member.getMobile())) {
            targetMember.setMobile(StringUtils.htmlEncode(member.getMobile()));
        }
        if (StringUtils.hasText(member.getEmail())) {
            targetMember.setEmail(StringUtils.htmlEncode(member.getEmail()));
        }
        if (StringUtils.hasText(member.getQqNo())) {
            targetMember.setQqNo(member.getQqNo());
        }
        if (StringUtils.hasText(member.getAddress())) {
            targetMember.setAddress(StringUtils.htmlEncode(member.getAddress()));
        }
        if (member.getStatus() != null) {
            targetMember.setStatus(member.getStatus());
        }
        // 更新头像
        if (avatar != null) {
            try {
                String path = "/user/" + UUIDUtil.getUUID();
                QiniuUtil.upload(avatar, path);
                targetMember.setHead(path);
            } catch (Exception e) {
                result.put("success", false);
                result.put("msg", "用户头像上传失败!稍候重试");
            }
        }
        boolean success = memberService.saveOrUpdate(targetMember);
        if (success) {
            result.put("success", success);
            result.put("msg", "保存成功");
        } else {
            result.put("success", success);
            result.put("msg", "保存失败! 稍候重试!");
        }
        return json(JSONObject.fromObject(result));
    }

//    /**
//     * 老接口, 废弃
//     * @return
//     */
//    @Deprecated
//    public Result orderList() {
//        Page pages = new Page(page, rows);
//        user = memberService.get(id);
//        List<Order> orderList = customerService.getOrderList(user, pages);
//        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(new Class[]{}, new String[]{});
//        return datagrid(orderList, pages.getTotalCount(), jsonConfig);
//    }


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

    public File getAvatar() {
        return avatar;
    }

    public void setAvatar(File avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getOrderProperty() {
        return orderProperty;
    }

    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
}
