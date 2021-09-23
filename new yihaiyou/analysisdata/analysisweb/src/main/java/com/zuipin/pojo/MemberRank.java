package com.zuipin.pojo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * MemberRank entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "member_rank")
public class MemberRank implements java.io.Serializable {
	
	// Fields
	
	private static final long	serialVersionUID	= -6140637619655829811L;
	private Long		id;
	private String		name;
	private Set<Member>	members	= new HashSet<Member>(0);
	
	// Constructors
	
	/** default constructor */
	public MemberRank() {
	}
	
	/** full constructor */
	public MemberRank(String name, Set<Member> members) {
		this.name = name;
		this.members = members;
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
	
	@Column(name = "NAME", length = 500)
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "memberRank")
	@JsonIgnore
	public Set<Member> getMembers() {
		return this.members;
	}
	
	public void setMembers(Set<Member> members) {
		this.members = members;
	}
	
}