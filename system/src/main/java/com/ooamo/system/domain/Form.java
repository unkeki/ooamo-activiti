package com.ooamo.system.domain;

import com.ooamo.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 表单
 */
public class Form extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String content;

	private Date applytime;

	private String applyer;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName(){return name;}

	public void setName(String name){
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getApplytime() {
		return applytime;
	}

	public void setApplytime(Date applytime) {
		this.applytime = applytime;
	}

	public String getApplyer() {
		return applyer;
	}

	public void setApplyer(String applyer) {
		this.applyer = applyer;
	}

	@Override
	public String toString() {
		return "Form{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", content='" + content + '\'' +
				", applytime=" + applytime +
				", applyer='" + applyer + '\'' +
				'}';
	}
}
