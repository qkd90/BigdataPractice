package com.data.data.hmly.service.base;

import java.io.Serializable;
import java.util.List;

public class TreeItem implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;
	private String menuid;
	private String menuname;
	private String url;
	private String icon;
	private boolean internalLink = false;
	private boolean leaf = true;
	private List<TreeItem> menus;
	
	public TreeItem() {
		
	}
	
	
	public TreeItem(String menuid, String menuname, String url, String icon,
			boolean leaf, List<TreeItem> menus) {
		super();
		this.menuid = menuid;
		this.menuname = menuname;
		this.url = url;
		this.icon = icon;
		this.leaf = leaf;
		this.menus = menus;
	}


	public String getMenuid() {
		return menuid;
	}


	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}


	public String getMenuname() {
		return menuname;
	}


	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getIcon() {
		return icon;
	}


	public void setIcon(String icon) {
		this.icon = icon;
	}


	public boolean isLeaf() {
		return leaf;
	}


	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}


	public List<TreeItem> getMenus() {
		return menus;
	}


	public void setMenus(List<TreeItem> menus) {
		this.menus = menus;
	}


	public boolean isInternalLink() {
		return internalLink;
	}


	public void setInternalLink(boolean internalLink) {
		this.internalLink = internalLink;
	}


	/* 
	 * 浅拷贝，并且不拷贝children属性
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		TreeItem object = new TreeItem();
		object.setMenuid(this.menuid);
		object.setMenuname(this.menuname);
		object.setUrl(this.url);
		object.setIcon(this.icon);
		object.setInternalLink(this.internalLink);
		object.setLeaf(this.leaf);
		return object;
	}
	
	
}
