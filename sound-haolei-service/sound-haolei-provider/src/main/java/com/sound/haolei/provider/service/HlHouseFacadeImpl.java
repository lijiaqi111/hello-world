package com.sound.haolei.provider.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.sound.haolei.constants.ConstantsSubstation;
import com.sound.haolei.facade.HlHouseFacade;
import com.sound.haolei.model.HlHouse;
import com.sound.haolei.provider.dao.HlHouseMapper;
import com.sound.haolei.provider.dao.HlSubstationMapper;
import com.sound.haolei.provider.util.ResultDataMap;
import com.sound.haolei.utils.CheckUtil;

@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class HlHouseFacadeImpl extends BaseFacadeImpl<HlHouse,HlHouseMapper> implements HlHouseFacade {
			
	@Autowired
	private HlHouseMapper hlHouseMapper;
	@Autowired
	private HlSubstationMapper hlSubstationMapper;

	/**
	 * 
	* @Title: selectAreaByPCC 
	* @Description: 预约上门回收小区查询接口
	* @return 参数说明
	* @author Lvshiyang
	* @date 2018年3月7日
	 */
	@Override
	public Map<String, Object> selectAreaByPCC(String publicnumberid){
		Map<String, Object> readyMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(CheckUtil.isEmpty(publicnumberid)){
			return ResultDataMap.getRtnCode("", "公众号ID不能为空", 1013);
		}
		publicnumberid = publicnumberid.trim();
		Map<String,String> typemid = hlSubstationMapper.selectTypeByPublicNumberId(publicnumberid);
		if(CheckUtil.isEmpty(typemid)){
			return ResultDataMap.getRtnCode("", "公众号ID不存在！", 1014);
		}
		Integer type = Integer.parseInt(String.valueOf(typemid.get("type")));
		Integer mid = Integer.parseInt(String.valueOf(typemid.get("mid")));
		if(type.equals(0)){
			readyMap = hlSubstationMapper.selectProvinceCityByPublicNumberId1(publicnumberid,mid);
			Integer cid = Integer.parseInt(String.valueOf(readyMap.get("cid")));
			List<Map<String,Object>> countyList = hlSubstationMapper.selectCountyList(cid);
			resultMap.put("pid", readyMap.get("pid"));
			resultMap.put("province", readyMap.get("province"));
			resultMap.put("cid", readyMap.get("cid"));
			resultMap.put("city", readyMap.get("city"));
			resultMap.put("countyList", countyList);
			return ResultDataMap.getRtnCode(resultMap, "", 0);
		}
		if(type.equals(1)){
			Map<String, Object> cityInfomap = hlSubstationMapper.selectCityInfo(String.valueOf(typemid.get("mid")));
			String citymid = String.valueOf(cityInfomap.get("id"));
			String city = cityInfomap.get("name").toString();
			Map<String,Object> pmap = hlSubstationMapper.selectProvinceCityByPublicNumberId2(citymid);
			readyMap.put("pid", pmap.get("pid"));
			readyMap.put("province", pmap.get("province"));
			readyMap.put("cid", citymid);
			readyMap.put("city", city);
			List<Map<String,Object>> countyList = hlSubstationMapper.selectCountyList(Integer.parseInt(citymid));
			readyMap.put("countyList", countyList);
			return ResultDataMap.getRtnCode(readyMap, "", 0);
		}
		return ResultDataMap.getRtnCode(readyMap, "请求异常！", -1);
	}

	@Override
	public HlHouseMapper getMapper() throws Exception {
		return this.hlHouseMapper;
	}
	/**
	 * 
	* @Title: selectCanAppointmentHouse 
	* @Description: TODO(预约上门回收服务亭查询接口) 
	* @param publicnumberid
	* @param countyId
	* @return 参数说明
	* @author Lvshiyang
	* @date 2018年3月8日 下午8:49:09
	 */
	@Override
	public Map<String, Object> selectCanAppointmentHouse(String publicnumberid, String countyId) {
		if(CheckUtil.isEmpty(publicnumberid)){
			return getRtnCode("公众号ID不能为空",1013);
		}
		if(CheckUtil.isEmpty(countyId)){
			return getRtnCode("所在区或县的ID不能为空",1013);
		}
		String allSpell = hlHouseMapper.selectSubstationAllspell(publicnumberid);
		List<Map<String,Object>> houseList = hlHouseMapper.selectCanAppointmentHouse(allSpell,countyId);
		return getSussRtn(houseList);
	}

	@Override
	public HlHouse selectByIdAndSubstationNameSpell(Integer id, String substationNameSpell) {
		return hlHouseMapper.selectByIdAndTableEnd(id,substationNameSpell);
	}
	/**
	 * 回收亭管理员状态修改
	 * @Title: updateAdminSattus
	 * @param adminId
	 * @param status
	 * @return Map    返回类型
	 * @throws
	 * @author wangzhanguo
	 * @date 2017年5月24日 下午12:25:21
	 */
	@Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> updateHouseStatus(Map<String,Object> paramMap) {
		Integer houseId = Integer.parseInt(paramMap.get("houseId").toString());
		Integer status = Integer.parseInt(paramMap.get("status").toString());
		try {
			// 状态：0 正常 1 冻结
			HlHouse hlHouse = new HlHouse();
			hlHouse.setId(houseId);
			hlHouse.setStatus(status);
			hlHouse.setSubstationNameSpell(paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());
			HlHouse hlHouse1 = hlHouseMapper.selectByPrimaryKey(houseId, paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());
			if(null == hlHouse1){
				return getFailRtn("没有此服务亭");
			}
			int num = hlHouseMapper.updateByPrimaryKeySelective(hlHouse);
			if(num == 1){
				return getSussRtn(null);
			}
			return getFailRtn("修改失败");
		} catch (Exception e) {
			e.printStackTrace();
			return getFailRtn("修改失败");
		}
    }

}
