package com.tihom.security.rbac.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author TiHom
 *
 */
public class AdminInfo {
	
	private Long id;
	/**
	 * 角色id 
	 */
	@NotBlank(message = "角色id不能为空")
	private Long roleId;
	/**
	 * 用户名
	 */
	@NotBlank(message = "用户名不能为空")
	private String username;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getRoleId() {
		return roleId;
	}


	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}
	
}