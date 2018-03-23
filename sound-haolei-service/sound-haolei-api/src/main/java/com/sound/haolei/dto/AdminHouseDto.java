package com.sound.haolei.dto;

import com.sound.haolei.model.HlHouse;

public class AdminHouseDto extends HlHouse {

	//省份名
	private String province;
	
	//城市名
	private String city;
	
	//区县名
	private String county;
	
	//创建用户名
	private String cuser;
	
	//修改用户名
	private String luser;

	public String getProvince() {
		return province == null ? "" : province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city == null ? "" : city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county == null ? "" : county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCuser() {
		return cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public String getLuser() {
		return luser;
	}

	public void setLuser(String luser) {
		this.luser = luser;
	}
	
	
	
}
