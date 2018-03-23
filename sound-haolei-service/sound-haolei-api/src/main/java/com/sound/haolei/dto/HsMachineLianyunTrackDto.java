package com.sound.haolei.dto;

import java.io.Serializable;

public class HsMachineLianyunTrackDto implements Serializable{
	
	//openId
    private String wechatOfficialAccountsId;
	//回收箱编号
	private String mchid;
	//手机号
	private String mobile;
	//卡号
	private String card_id;
	//回收类型
	private String[] type;
	//回收重量
	private String weight;
	//获取乐豆
	private String cash_point;
	//详细地址
	private String address;
	//小区
	private String area;
	//开始时间
	private String starttime;
	//结束时间
	private String endtime;
	//投放时间
	private String ctime;
	
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getWechatOfficialAccountsId() {
		return wechatOfficialAccountsId;
	}
	public void setWechatOfficialAccountsId(String wechatOfficialAccountsId) {
		this.wechatOfficialAccountsId = wechatOfficialAccountsId;
	}
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String[] getType() {
		return type;
	}
	public void setType(String[] type) {
		this.type = type;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getCash_point() {
		return cash_point;
	}
	public void setCash_point(String cash_point) {
		this.cash_point = cash_point;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	
	
	
	
	

}
