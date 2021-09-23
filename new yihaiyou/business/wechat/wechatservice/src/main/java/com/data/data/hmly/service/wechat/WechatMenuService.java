package com.data.data.hmly.service.wechat;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.wechat.dao.WechatAccountMenuDao;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.data.data.hmly.service.wechat.entity.WechatAccountMenu;
import com.data.data.hmly.service.wechat.entity.WechatResource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by vacuity on 15/11/25.
 */

@Service
public class WechatMenuService {

    @Resource
    private WechatAccountMenuDao wechatAccountMenuDao;
    @Resource
    private WechatAccountService wechatAccountService;
    @Resource
    private WechatResourceService wechatResourceService;


    public WechatAccountMenu get(Long id) {
        return wechatAccountMenuDao.get(id);
    }

    public void saveOrUpdate(WechatAccountMenu wechatAccountMenu) {
        wechatAccountMenuDao.saveOrUpdate(wechatAccountMenu, wechatAccountMenu.getId());
    }

    public List<WechatAccountMenu> getMenu(Long accountId) {
        List<WechatAccountMenu> menuList = wechatAccountMenuDao.getMenuList(accountId);
        for (WechatAccountMenu wechatAccountMenu : menuList) {
            wechatAccountMenu.setText(wechatAccountMenu.getMenuName());
        }

        List<WechatAccountMenu> menu = new ArrayList<WechatAccountMenu>();
        Map<Long, WechatAccountMenu> menuMap = new HashMap<Long, WechatAccountMenu>();
        int i = 0;
        // 取出一级菜单
        for (; i < menuList.size(); i++) {
            if (menuList.get(i).getLevel() == 1) {
                menu.add(menuList.get(i));
                menuMap.put(menuList.get(i).getId(), menuList.get(i));
            } else {
                break;
            }
        }
        // 处理二级菜单
        for (; i < menuList.size(); i++) {
            if (menuMap.get(menuList.get(i).getParentMenu().getId()) == null) {
                continue;
            }
            WechatAccountMenu parentMenu = menuMap.get(menuList.get(i).getParentMenu().getId());
            if (parentMenu.getChildren() == null) {
                List<WechatAccountMenu> children = new ArrayList<WechatAccountMenu>();
                children.add(menuList.get(i));
                parentMenu.setChildren(children);
            } else {
                List<WechatAccountMenu> children = parentMenu.getChildren();
                children.add(menuList.get(i));
                parentMenu.setChildren(children);
            }
        }
        return menu;
    }

    /**
     * vacuity
     *
     * 删除所有旧菜单，为下一步插入所有菜单做准备
     * @param accountId
     */
    public void doDeleteMenu(Long accountId) {
        List<WechatAccountMenu> menuList = wechatAccountMenuDao.getMenuList(accountId);
        wechatAccountMenuDao.deleteAll(menuList);
    }

    /**
     * vacuity
     *
     * 存储整个菜单
     *
     * @param accountId
     * @param user
     * @param companyUnit
     * @param data
     */
    public void saveMenu(Long accountId, SysUser user, SysUnit companyUnit, List<Map<String, Object>> data) {

        // 删除原有数据
        doDeleteMenu(accountId);

        WechatAccount wechatAccount = wechatAccountService.get(accountId);


        // 存储所有菜单中用到的资源ID
        HashSet<Long> resourceIdSet = new HashSet<Long>();

        // 已键值对的方式把Menu和资源ID做关联
        HashMap<String, Long> resourceIdMap = new HashMap<String, Long>();

        // 一级菜单列表
        List<WechatAccountMenu> menuList = new ArrayList<WechatAccountMenu>();
        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> parentData = data.get(i);
            List<Map<String, Object>> children = (List<Map<String, Object>>) parentData.get("children");
            WechatAccountMenu parentMenu = new WechatAccountMenu();
            parentMenu.setMenuName(parentData.get("menuName").toString());
            parentMenu.setLevel(1);
            parentMenu.setWechatAccount(wechatAccount);
            parentMenu.setCompanyUnit(companyUnit);
            parentMenu.setCreateTime(new Date());
            parentMenu.setUser(user);
            parentMenu.setOrderNo(i + 1);

            List<WechatAccountMenu> childMenuList = new ArrayList<WechatAccountMenu>();

            if (children.size() == 0) {
                // 处理资源
                if (parentData.get("url") == null || "".equals(parentData.get("url"))) {
                    String key = parentMenu.getLevel() + "_" + parentMenu.getOrderNo();
                    Long resourceId = Long.parseLong(parentData.get("resourceId").toString());
                    resourceIdSet.add(resourceId);
                    resourceIdMap.put(key, resourceId);
                } else {
                    parentMenu.setUrl(parentData.get("url").toString());
                }
            } else {
                // 二级菜单
                for (int j = 0; j < children.size(); j++) {
                    Map<String, Object> childData = children.get(j);
                    WechatAccountMenu childMenu = new WechatAccountMenu();
                    childMenu.setMenuName(childData.get("menuName").toString());
                    childMenu.setLevel(2);
                    childMenu.setWechatAccount(wechatAccount);
                    childMenu.setCompanyUnit(companyUnit);
                    childMenu.setCreateTime(new Date());
                    childMenu.setUser(user);
                    childMenu.setOrderNo(j + 1);
                    childMenuList.add(childMenu);

                    // 处理资源
                    if (childData.get("url") == null || "".equals(childData.get("url"))) {
                        String key = childMenu.getLevel() + "_" + parentMenu.getOrderNo() + "_" + childMenu.getOrderNo();

                        Long resourceId = Long.parseLong(childData.get("resourceId").toString());
                        resourceIdSet.add(resourceId);

                        resourceIdMap.put(key, resourceId);
                    } else {
                        childMenu.setUrl(childData.get("url").toString());
                    }
                }
            }
            parentMenu.setChildren(childMenuList);
            menuList.add(parentMenu);
        }

        // 批量查询资源并存入Map中方便后续取出
        Map<Long, WechatResource> resourceMap = new HashMap<Long, WechatResource>();
        if (resourceIdSet.size() > 0) {
            List<WechatResource> wechatResourceList = wechatResourceService.getByIdSet(resourceIdSet);

            for (WechatResource wechatResource : wechatResourceList) {
                resourceMap.put(wechatResource.getId(), wechatResource);
            }
        }

        // 处理父菜单和资源并存入数据库
        for (WechatAccountMenu pMenu : menuList) {
            String key = pMenu.getLevel() + "_" + pMenu.getOrderNo();
            if (resourceIdMap.get(key) != null) {
                pMenu.setWechatResource(resourceMap.get(resourceIdMap.get(key)));
            }
            saveOrUpdate(pMenu);
            for (WechatAccountMenu cMenu : pMenu.getChildren()) {
                String ckey = cMenu.getLevel() + "_" + pMenu.getOrderNo() + "_" + cMenu.getOrderNo();
                if (resourceMap.get(resourceIdMap.get(ckey)) != null) {
                    cMenu.setWechatResource(resourceMap.get(resourceIdMap.get(ckey)));
                }
                cMenu.setParentMenu(pMenu);
                saveOrUpdate(cMenu);
            }
        }

    }


    public Integer getCount(Long accountId, Integer level) {
        return wechatAccountMenuDao.getCount(accountId, level);
    }

}
