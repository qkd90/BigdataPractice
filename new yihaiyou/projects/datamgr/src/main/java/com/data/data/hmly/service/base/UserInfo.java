package com.data.data.hmly.service.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class UserInfo implements java.io.Serializable {
	private static final long serialVersionUID = 4512635928792539546L;
	private Long userId;
	private String loginName;
	private String password;
    private String userName;
    private String nickName;
	private String portalType;

	private Long companyId;
	private String companyName;
	private String companyStatus;

    private boolean status = true;	// 登入时默认为有效，登出时置为无效，待需处理任务结束后再销毁

    // 用户导航菜单
	private List<TreeItem> navigationTree = new ArrayList<TreeItem>();
    // 功能权限
    private Set<String> notHasFuncSet = new HashSet<String>();	// 需要权限控制未授权的url集合

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPortalType() {
		return portalType;
	}

	public void setPortalType(String portalType) {
		this.portalType = portalType;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	public List<TreeItem> getNavigationTree() {
		return navigationTree;
	}

	/**
	 * 构建菜单树
	 * @param menus 需先按父节点、排序字段进行排序
	 */
//	public void setNavigationTree(List<Menu> menus) {
//		if (menus != null && menus.size() > 0) {
//			String rootId = null;
//			for (Menu menu : menus) {	// 找出根节点
//				if (BizConstants.PARENT_MENU_ID.equals(menu.getParentMenuId())) {
//					rootId = menu.getId();
//					break ;
//				}
//			}
//			if (StringUtil.isBlank(rootId)) {
//				return ;
//			}
//			Map<String, TreeItem> map = new HashMap<String, TreeItem>();
//			for (Menu menu : menus) {
//				TreeItem item = new TreeItem();
//				item.setMenuid(menu.getId());
//				item.setMenuname(menu.getMenuName());
//				item.setUrl(menu.getMenuEntry());
//				item.setInternalLink(!BizConstants.COMMON_FLAG_FALSE.equals(menu.getInternalLinkFlag()));
//				item.setLeaf(BizConstants.COMMON_FLAG_TRUE.equals(menu.getLeafFlag()));
//				if (item.isLeaf()) {
//					item.setIcon("icon-sys");
//				}
//				if (rootId.equals(menu.getParentMenuId())) {
//					item.setMenus(new ArrayList<TreeItem>());
//					navigationTree.add(item);
//					map.put(menu.getId(), item);
//				} else {
//					TreeItem parentItem = map.get(menu.getParentMenuId());
//					if (parentItem != null) {
//						parentItem.getMenus().add(item);
//					}
//				}
//			}
//		}
//	}

	public Set<String> getNotHasFuncSet() {
		return notHasFuncSet;
	}

//	public void setNotHasFuncSet(List<Menu> menus) {
//		if (menus != null && menus.size() > 0) {
//			for (Menu menu : menus) {
//				if (StringUtil.isNotBlank(menu.getMenuEntry())) {
//					notHasFuncSet.add(menu.getMenuEntry());
//				}
//			}
//		}
//	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}



}
