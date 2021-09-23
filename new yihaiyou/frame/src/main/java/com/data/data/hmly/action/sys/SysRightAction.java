package com.data.data.hmly.action.sys;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysActionLogService;
import com.data.data.hmly.service.SysRightService;
import com.data.data.hmly.service.entity.SysMenu;
import com.data.data.hmly.service.entity.SysRole;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.SysUserRole;
import com.data.data.hmly.service.vo.TreeGridRightVo;
import com.data.data.hmly.util.Jacksons;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.Constants;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjj
 * @date 2015年3月30日
 * @TODO 角色-权限操作
 */
public class SysRightAction extends FrameBaseAction implements ModelDriven<SysRole> {
    private static final long serialVersionUID = 1L;
    @Resource
    private SysRightService sysRightService;
    @Resource
    private SysActionLogService sysActionLogService;
    private String json;
    private Integer page = 1;
    private Integer rows = 10;
    private SysRole role = new SysRole();
    private Map<String, Object> map = new HashMap<String, Object>();
    private List<SysMenu> menus;
    private Long roleId;
    private String m_rights;
    private String r_rights;
    private final SysUser sysUser = getLoginUser();
    private String content = "";
    String account = ""; // 用于记录当前登录用户账号
    String name = ""; // 用于记录当前登录用户姓名

    public Result manage() {
        return dispatch();
    }

    /**
     * 功能描述：查看所有权限
     *
     * @return
     * @author : cjj 陈俊杰 @CreatedTime : 2015年4月7日上午11:30:20
     */
    public Result showRight() {
        Map<String, Object> rightMap = null;
        List<SysMenu> userRight = (List<SysMenu>) getSession().getAttribute("menuTree");
        if (isSupperAdmin()) {
            userRight = sysRightService.findAllMenu(0L, null);
        }
        if (roleId == -2) {
            rightMap = sysRightService.findAllMenuRight();
        } else {
            rightMap = sysRightService.findMenuRight(roleId);
        }
        List<TreeGridRightVo> menus = sysRightService.findRight(rightMap, userRight);
//		for (SysUserRole role : getLoginUser().getSysRoles()) {
//			if (role.getRole().getId().intValue() == -1) {
//				break;
//			}
//		}
//		 if (noSupperAdmin) {
//		 menus = filterNoRightMenu(menus);
//		 }
        map.put("rows", menus);
        json = Jacksons.me().readAsString(map);
        return json(JSONObject.fromObject(json));
    }

    private List<TreeGridRightVo> filterNoRightMenu(List<TreeGridRightVo> menus) {
        List<TreeGridRightVo> newMenu = new ArrayList<TreeGridRightVo>();
        for (TreeGridRightVo menu : menus) {
            if (menu.getHasRight() != 0) {
                menu.setChildren(filterNoRightMenu(menu.getChildren()));
                newMenu.add(menu);
            }
        }
        return newMenu;
    }

    /**
     * 功能描述：保存权限
     *
     * @return
     * @author : cjj 陈俊杰 @CreatedTime : 2015年4月8日上午9:40:48
     */
    public Result saveRight() {
        try {
            SysUser user = getLoginUser();
            for (SysUserRole role : user.getSysRoles()) {
                if (role.getRole().getId().longValue() == roleId) {
                    simpleResult(map, false, "不能修改自身权限，请联系管理员!");
                    return jsonResult(map);
                }
            }
            sysRightService.updateRight(roleId, m_rights, r_rights);
            map.put("success", true);
            if (sysUser != null) {
                account = sysUser.getAccount();
                name = sysUser.getUserName();
            }
            content = "账号：" + account + "保存一个用户权限，权限ID为：" + roleId;
            sysActionLogService.addSysLog(account, name, "sysRight", "保存", content);
        } catch (Exception e) {
            simpleResult(map, false, "保存权限出错!");
            slog(Constants.log_error, "保存权限出错!", e);
        }
        return jsonResult(map);
    }

    @Override
    public String getJson() {
        return json;
    }

    @Override
    public void setJson(String json) {
        this.json = json;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    @Override
    public SysRole getModel() {
        return role;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public List<SysMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<SysMenu> menus) {
        this.menus = menus;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getM_rights() {
        return m_rights;
    }

    public void setM_rights(String m_rights) {
        this.m_rights = m_rights;
    }

    public String getR_rights() {
        return r_rights;
    }

    public void setR_rights(String r_rights) {
        this.r_rights = r_rights;
    }

}
