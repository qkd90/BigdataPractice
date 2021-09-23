package com.data.data.hmly.service.restaurant.entity;


import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.entity.User;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "delicacy")
public class Delicacy extends com.framework.hibernate.util.Entity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(name = "food_name")
	private String name;
	@Column(name = "price")
	private Double price;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_code")
	private TbArea city;
	@Column(name = "cuisine")
	private String cuisine;
	@Column(name = "taste")
	private String taste;
	@Column(name = "efficacy")
	private String efficacy;
	@Column(name = "cover")
	private String cover;
    @Column(name = "status")
    private Integer status;
    @Column(name = "ranking")
    private Integer ranking;

	@Transient
	private String longIntro;
	@Transient
	private String shortIntro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	@OneToOne(mappedBy = "delicacy", fetch = FetchType.LAZY)
	private DelicacyExtend extend;
	@OneToMany(mappedBy = "delicacy", fetch = FetchType.LAZY)
	@Where(clause = "status='1'")
	private List<DelicacyRestaurant> restaurants;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "targetId", referencedColumnName = "id")
	@Where(clause = "targetType='DELICACY'")
	protected Set<LabelItem> labelItems;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "targetId", referencedColumnName = "id")
	@Where(clause = "type='delicacy'")
	private List<Comment> delicacyCommentList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public TbArea getCity() {
		return city;
	}

	public void setCity(TbArea city) {
		this.city = city;
	}

	public String getCuisine() {
		return cuisine;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}

	public String getTaste() {
		return taste;
	}

	public void setTaste(String taste) {
		this.taste = taste;
	}

	public String getEfficacy() {
		return efficacy;
	}

	public void setEfficacy(String efficacy) {
		this.efficacy = efficacy;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public DelicacyExtend getExtend() {
		return extend;
	}

	public void setExtend(DelicacyExtend extend) {
		this.extend = extend;
	}

	public List<DelicacyRestaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(List<DelicacyRestaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public Set<LabelItem> getLabelItems() {
		return labelItems;
	}

	public void setLabelItems(Set<LabelItem> labelItems) {
		this.labelItems = labelItems;
	}

	public List<Comment> getDelicacyCommentList() {
		return delicacyCommentList;
	}

	public void setDelicacyCommentList(List<Comment> delicacyCommentList) {
		this.delicacyCommentList = delicacyCommentList;
	}

	public String getShortIntro() {
		return shortIntro;
	}

	public void setShortIntro(String shortIntro) {
		this.shortIntro = shortIntro;
	}

	public String getLongIntro() {
		return longIntro;
	}

	public void setLongIntro(String longIntro) {
		this.longIntro = longIntro;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
}
