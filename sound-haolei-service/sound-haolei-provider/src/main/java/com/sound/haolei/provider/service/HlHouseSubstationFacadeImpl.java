package com.sound.haolei.provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.sound.haolei.facade.HlHouseFacade;
import com.sound.haolei.facade.HlHouseSubstationFacade;
import com.sound.haolei.model.HlHouse;
import com.sound.haolei.model.HlHouseSubstation;
import com.sound.haolei.provider.dao.HlHouseSubstationMapper;

@Service(
		version = "1.0.0",
		application = "${dubbo.application.id}",
		protocol = "${dubbo.protocol.id}",
		registry = "${dubbo.registry.id}"
)
public class HlHouseSubstationFacadeImpl extends BaseFacadeImpl<HlHouseSubstation, HlHouseSubstationMapper>
		implements HlHouseSubstationFacade {
	@Autowired
	private HlHouseSubstationMapper hlHouseSubstationMapper;
	@Autowired
	private HlHouseFacade hlHouseFacade;
	
	@Override
	public HlHouseSubstationMapper getMapper() throws Exception {
		return this.hlHouseSubstationMapper;
	}

	/**
	 * 先往中间表插入，然后取中间表的id给house插入
	 * @author liuyang
	 * @date2017年7月10日15:17:11
	 * @param house
	 * @param subid	分站id
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer saveSubAndHouse(HlHouse house, Object subid) {
		HlHouseSubstation hsub = new HlHouseSubstation();
		hsub.setSubstationId(Integer.parseInt(subid+""));
		hlHouseSubstationMapper.insertSelective(hsub);
		house.setId(hsub.getId());
		try {
			hlHouseFacade.save(house);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return house.getId();
	}
	
}
