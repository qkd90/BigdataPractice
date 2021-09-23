package com.zuipin.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * WebStore entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "web_store")
public class WebStore extends com.framework.hibernate.util.Entity implements java.io.Serializable {
	
	// Fields
	
	private static final long	serialVersionUID	= 1L;
	private Long				id;
	private String				name;
	private String				simpleName;
	private String				webAddr;
	private Integer				seq;
	private String				shopName;
	private String				typeName;
	private String				sellerName;
	private String				sellerTel;
	private String				sellerPost;
	private String				sellerAddress;
	private String				origin;
	private DeliverySite		deliverySite;				// 网店关联配送点
															
	// Constructors
	
	/** default constructor */
	public WebStore() {
	}
	
	public WebStore(Long id) {
		this.id = id;
	}
	
	/** full constructor */
	public WebStore(String name, String simpleName, String webAddr, Integer seq, String shopName, String typeName, String sellerName, String sellerTel, String sellerPost,
			String sellerAddress, String origin) {
		this.name = name;
		this.simpleName = simpleName;
		this.webAddr = webAddr;
		this.seq = seq;
		this.shopName = shopName;
		this.typeName = typeName;
		this.sellerName = sellerName;
		this.sellerTel = sellerTel;
		this.sellerPost = sellerPost;
		this.sellerAddress = sellerAddress;
		this.origin = origin;
	}
	
	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "NAME", length = 50)
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "SIMPLE_NAME", length = 50)
	public String getSimpleName() {
		return this.simpleName;
	}
	
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	
	@Column(name = "WEB_ADDR", length = 100)
	public String getWebAddr() {
		return this.webAddr;
	}
	
	public void setWebAddr(String webAddr) {
		this.webAddr = webAddr;
	}
	
	@Column(name = "SEQ")
	public Integer getSeq() {
		return this.seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	@Column(name = "SHOP_NAME")
	public String getShopName() {
		return this.shopName;
	}
	
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	@Column(name = "TYPE_NAME")
	public String getTypeName() {
		return this.typeName;
	}
	
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Column(name = "SELLER_NAME")
	public String getSellerName() {
		return this.sellerName;
	}
	
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	
	@Column(name = "SELLER_TEL")
	public String getSellerTel() {
		return this.sellerTel;
	}
	
	public void setSellerTel(String sellerTel) {
		this.sellerTel = sellerTel;
	}
	
	@Column(name = "SELLER_POST")
	public String getSellerPost() {
		return this.sellerPost;
	}
	
	public void setSellerPost(String sellerPost) {
		this.sellerPost = sellerPost;
	}
	
	@Column(name = "SELLER_ADDRESS")
	public String getSellerAddress() {
		return this.sellerAddress;
	}
	
	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}
	
	@Column(name = "ORIGIN")
	public String getOrigin() {
		return this.origin;
	}
	
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "deliverySite")
	public DeliverySite getDeliverySite() {
		return deliverySite;
	}
	
	public void setDeliverySite(DeliverySite deliverySite) {
		this.deliverySite = deliverySite;
	}
	
}