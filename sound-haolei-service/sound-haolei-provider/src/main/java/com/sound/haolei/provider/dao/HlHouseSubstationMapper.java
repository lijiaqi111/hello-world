package com.sound.haolei.provider.dao;

import org.apache.ibatis.annotations.Param;

import com.sound.haolei.model.HlHouseSubstation;

public interface HlHouseSubstationMapper extends IBaseMapper<HlHouseSubstation> {

	//根据回收亭id查询
	HlHouseSubstation selectByHouseId(@Param("houseId") Integer houseId);
	
}