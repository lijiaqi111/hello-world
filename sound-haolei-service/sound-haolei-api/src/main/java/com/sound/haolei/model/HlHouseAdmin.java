package com.sound.haolei.model;

import java.util.Date;

public class HlHouseAdmin extends BaseModel{
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	

	//columns START
    /**
     * id  
     */ 	
	private Integer id;
    /**
     * 分站id  
     */ 	
	private Integer substationId;
    /**
     * 管理员手机号  
     */ 	
	private String mobile;
    /**
     * 身份证号  
     */ 	
	private String identityCard;
    /**
     * 银行卡号  
     */ 	
	private String bankCardNo;
    /**
     * 银行卡名称  
     */ 	
	private String bankCardName;
    /**
     * 管理员昵称  
     */ 	
	private String nickname;
    /**
     * 管理员密码  
     */ 	
	private String passwd;
    /**
     * 状态：0 正常 1 冻结  
     */ 	
	private Byte status;
	/**
	 * 是否是亭长：0 否 1 是  
	 */ 	
	private Byte houseLeader;
	
	/**
     * 管理端管理员角色 id，即 hl_house_admin_role 表 id  
     */ 	
	private Integer houseAdminRoleId;
    /**
     * 管理员现住址  
     */ 	
	private String address;
    /**
     * 创建时间  
     */ 	
	private Date ctime;
    /**
     * 最后登录时间  
     */ 	
	private Date ltime;
	//columns END

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return this.id;
	}

	public Integer getSubstationId() {
		return this.substationId;
	}
	
	public void setSubstationId(Integer substationId) {
		this.substationId = substationId;
	}
	public String getMobile() {
		return this.mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIdentityCard() {
		return this.identityCard;
	}
	
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getBankCardNo() {
		return this.bankCardNo;
	}
	
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getBankCardName() {
		return this.bankCardName;
	}
	
	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}
	public String getNickname() {
		return this.nickname;
	}
	
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	 public Byte getHouseLeader() {
		return houseLeader;
	}

	public void setHouseLeader(Byte houseLeader) {
		this.houseLeader = houseLeader;
	}
	public String getPasswd() {
		return this.passwd;
	}
	
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public Byte getStatus() {
		return this.status;
	}
	
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Integer getHouseAdminRoleId() {
		return this.houseAdminRoleId;
	}
	
	public void setHouseAdminRoleId(Integer houseAdminRoleId) {
		this.houseAdminRoleId = houseAdminRoleId;
	}
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getCtime() {
		return this.ctime;
	}
	
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Date getLtime() {
		return this.ltime;
	}
	
	public void setLtime(Date ltime) {
		this.ltime = ltime;
	}

}

