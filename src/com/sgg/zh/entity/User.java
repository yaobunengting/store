package com.sgg.zh.entity;

import java.time.LocalDate;
import java.util.Date;

/**
 * 用户实体
 * 
 * @author Administrator
 *
 */
public class User {
	private String uid;
	private String username;
	private String password;

	private String name;
	private String email;
	private String telephpne;

	private Date birthday;
	private String sex;
	private Integer state = 0; // 激活状态 值为1:激活 为0:未激活
	private String code;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephpne() {
		return telephpne;
	}

	public void setTelephpne(String telephpne) {
		this.telephpne = telephpne;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", name=" + name + ", email="
				+ email + ", telephpne=" + telephpne + ", birthday=" + birthday + ", sex=" + sex + ", state=" + state
				+ ", code=" + code + "]";
	}

}
