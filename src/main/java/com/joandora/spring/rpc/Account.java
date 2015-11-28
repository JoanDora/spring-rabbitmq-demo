package com.joandora.spring.rpc;

import java.io.Serializable;
/**
 * pojo类	
 */
public class Account implements Serializable {
	
	private static final long serialVersionUID = 6033835389174582808L;
	
	private String id;
	private String name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
