package com.sound.haolei.facade;

import com.sound.haolei.model.HlHouse;
import com.sound.haolei.model.HlHouseSubstation;

/**
 * @author liuyang
 */
public interface HlHouseSubstationFacade  
			extends BaseFacade<HlHouseSubstation> {
	/**
	 * 先往中间表插入，然后取中间表的id给house插入
	 * @author liuyang
	 * @date2017年7月10日15:17:11
	 * @param house
	 * @param subid 分站id
	 */
	Integer saveSubAndHouse(HlHouse house, Object subid);

}
