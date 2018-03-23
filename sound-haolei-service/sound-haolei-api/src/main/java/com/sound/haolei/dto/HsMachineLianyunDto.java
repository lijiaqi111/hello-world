package com.sound.haolei.dto;

import com.sound.haolei.model.HsMachineLianyun;
import com.sound.haolei.model.MapCity;
import com.sound.haolei.model.MapCountry;
import com.sound.haolei.model.MapProvince;

/**
 * @author liuyang
 * @date 2018年3月9日10:51:29
 */
public class HsMachineLianyunDto extends HsMachineLianyun {
    
	private MapProvince province;

    private MapCity city;

    private MapCountry country;

	public MapProvince getProvince() {
		return province;
	}

	public void setProvince(MapProvince province) {
		this.province = province;
	}

	public MapCity getCity() {
		return city;
	}

	public void setCity(MapCity city) {
		this.city = city;
	}

	public MapCountry getCountry() {
		return country;
	}

	public void setCountry(MapCountry country) {
		this.country = country;
	}
    
}