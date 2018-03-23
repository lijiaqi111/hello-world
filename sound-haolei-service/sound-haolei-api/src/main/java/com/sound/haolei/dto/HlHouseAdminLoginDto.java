package com.sound.haolei.dto;

import java.io.Serializable;
import java.util.Date;

public class HlHouseAdminLoginDto implements Serializable{
	// 回收亭管理员id
	private Integer admin_id;
	// 回收亭管理员手机号
	private String admin_mobile;
	//回收亭管理员角色id;
	private Integer houseAdminRoleId;
	//回收亭管理员角色名称;
	private String houseAdminRoleName;
	// 回收亭管理员昵称
	private String admin_nickname;
	// 回收亭管理员登录密码
	private String admin_password;
	// 回收亭管理员状态  0 正常 1 冻结
	private Byte admin_status;
	// 回收亭管理员创建时间
	private Date admin_ctime;
	// 回收亭管理员最近登录时间
	private Date admin_ltime;
	// 回收亭id
	private Integer house_id;
	// 回收亭昵称
	private String house_nickname;
	// 回收亭所在省份id
	private Integer house_province_id;
	// 回收亭所在省份名称
	private String house_province_name;
	// 回收亭所在城市id
	private Integer house_city_id;
	// 回收亭所在城市名称
	private String house_city_name;
	// 回收亭所在县区id
	private Integer house_county_id;
	// 回收亭所在县区名称
	private String house_county_name;
	// 回收亭所在地详细地址
	private String house_address;
	
	public String getHouseAdminRoleName() {
		return houseAdminRoleName;
	}
	public void setHouseAdminRoleName(String houseAdminRoleName) {
		this.houseAdminRoleName = houseAdminRoleName;
	}
	public Integer getHouseAdminRoleId() {
		return houseAdminRoleId;
	}
	public void setHouseAdminRoleId(Integer houseAdminRoleId) {
		this.houseAdminRoleId = houseAdminRoleId;
	}
	public Integer getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(Integer admin_id) {
		this.admin_id = admin_id;
	}
	public String getAdmin_mobile() {
		return admin_mobile;
	}
	public void setAdmin_mobile(String admin_mobile) {
		this.admin_mobile = admin_mobile;
	}
	public String getAdmin_nickname() {
		return admin_nickname;
	}
	public void setAdmin_nickname(String admin_nickname) {
		this.admin_nickname = admin_nickname;
	}
	public Byte getAdmin_status() {
		return admin_status;
	}
	public void setAdmin_status(Byte admin_status) {
		this.admin_status = admin_status;
	}
	public Date getAdmin_ctime() {
		return admin_ctime;
	}
	public void setAdmin_ctime(Date admin_ctime) {
		this.admin_ctime = admin_ctime;
	}
	public Date getAdmin_ltime() {
		return admin_ltime;
	}
	public void setAdmin_ltime(Date admin_ltime) {
		this.admin_ltime = admin_ltime;
	}
	public Integer getHouse_id() {
		return house_id;
	}
	public void setHouse_id(Integer house_id) {
		this.house_id = house_id;
	}
	public String getHouse_nickname() {
		return house_nickname;
	}
	public void setHouse_nickname(String house_nickname) {
		this.house_nickname = house_nickname;
	}
	public Integer getHouse_province_id() {
		return house_province_id;
	}
	public void setHouse_province_id(Integer house_province_id) {
		this.house_province_id = house_province_id;
	}
	public String getHouse_province_name() {
		return house_province_name;
	}
	public void setHouse_province_name(String house_province_name) {
		this.house_province_name = house_province_name;
	}
	public Integer getHouse_city_id() {
		return house_city_id;
	}
	public void setHouse_city_id(Integer house_city_id) {
		this.house_city_id = house_city_id;
	}
	public String getHouse_city_name() {
		return house_city_name;
	}
	public void setHouse_city_name(String house_city_name) {
		this.house_city_name = house_city_name;
	}
	public Integer getHouse_county_id() {
		return house_county_id;
	}
	public void setHouse_county_id(Integer house_county_id) {
		this.house_county_id = house_county_id;
	}
	public String getHouse_county_name() {
		return house_county_name;
	}
	public void setHouse_county_name(String house_county_name) {
		this.house_county_name = house_county_name;
	}
	public String getHouse_address() {
		return house_address;
	}
	public void setHouse_address(String house_address) {
		this.house_address = house_address;
	}
	public String getAdmin_password() {
		return admin_password;
	}
	public void setAdmin_password(String admin_password) {
		this.admin_password = admin_password;
	}
	@Override
	public String toString() {
		return "HlHouseAdminLoginDto [admin_id=" + admin_id + ", admin_mobile=" + admin_mobile + ", admin_nickname="
				+ admin_nickname + ", admin_password=" + admin_password + ", admin_status=" + admin_status
				+ ", admin_ctime=" + admin_ctime + ", admin_ltime=" + admin_ltime + ", house_id=" + house_id
				+ ", house_nickname=" + house_nickname + ", house_province_id=" + house_province_id
				+ ", house_province_name=" + house_province_name + ", house_city_id=" + house_city_id
				+ ", house_city_name=" + house_city_name + ", house_county_id=" + house_county_id
				+ ", house_county_name=" + house_county_name + ", house_address=" + house_address + ", getAdmin_id()="
				+ getAdmin_id() + ", getAdmin_mobile()=" + getAdmin_mobile() + ", getAdmin_nickname()="
				+ getAdmin_nickname() + ", getAdmin_status()=" + getAdmin_status() + ", getAdmin_ctime()="
				+ getAdmin_ctime() + ", getAdmin_ltime()=" + getAdmin_ltime() + ", getHouse_id()=" + getHouse_id()
				+ ", getHouse_nickname()=" + getHouse_nickname() + ", getHouse_province_id()=" + getHouse_province_id()
				+ ", getHouse_province_name()=" + getHouse_province_name() + ", getHouse_city_id()="
				+ getHouse_city_id() + ", getHouse_city_name()=" + getHouse_city_name() + ", getHouse_county_id()="
				+ getHouse_county_id() + ", getHouse_county_name()=" + getHouse_county_name() + ", getHouse_address()="
				+ getHouse_address() + ", getAdmin_password()=" + getAdmin_password() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
