package com.sound.haolei.facade;

import java.util.Map;

import com.sound.haolei.model.HlHouseAdmin;

/**
 * @author liuyang
 */
public interface HlHouseAdminFacade extends BaseFacade<HlHouseAdmin> {
	
	/**
	 * 回收亭管理员添加回收亭
	 * @Title: addHousepOfAdmin 
	 * @param adminId
	 * @param mids
	 * @return Map    返回类型 
	 * @throws 
	 * @author wangzhanguo
	 * @date 2017年5月24日 下午12:25:21
	 */
	public Map<String, Object> addHousepOfAdmin(int adminId, Integer[] mids);

	/**
	 * 回收亭管理员状态修改
	 * @Title: updateAdminSattus 
	 * @param adminId
	 * @param status
	 * @return Map    返回类型 
	 * @author liuyang
	 * @date 2018年3月14日15:30:50
	 */
	public Map<String, Object> updateAdminStatus(int adminId, int status);
	
	/**
	 *  添加或修改回收亭管理员
	 * @param houseAdmin
	 */
	public Map<String, Object> saveOrUpdate(String houseId, HlHouseAdmin houseAdmin, String sell) throws Exception;

	/**
	 * 
	 * @Title: isHouseLeader 
	 * @Description: 判断此服务亭是否有亭长，且此人是否是此亭亭长
	 * @param houseId 服务亭id
	 * @param adminId 
	 * @param hSSpell 
 	 * @return Integer
	 * @throws 
	 * @author liuyang
	 * @date 2017年12月19日16:44:05
	 */
	public Integer isHouseLeader(Integer houseId, Integer adminId, String hSSpell);

}
