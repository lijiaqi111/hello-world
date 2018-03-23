package com.sound.haolei.provider.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sound.haolei.constants.ConstantsSubstation;
import com.sound.haolei.model.HlHouseAdmin;

public interface HlHouseAdminMapper extends IBaseMapper<HlHouseAdmin>{
	/**
	 * 根据手机号查询管理员信息
	* @Title: selectByMobile 
	* @param mobile
	* @return    设定文件 
	* @return HlHouseAdmin    返回类型 
	* @throws 
	* @author wanghancheng
	* @date 2017年5月23日 下午3:38:56
	 */
	HlHouseAdmin selectByMobile(String mobile);
	
	/**
	 * 根据手机号查询管理员和回收亭详细信息
	* @Title: selectFullInfoByMobile 
	* @param mobile
	* @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	* @author wanghancheng
	* @date 2017年6月7日 上午9:39:22
	 */
	List<Map<String,Object>> selectFullInfoByMobile(@Param("mobile") String mobile, @Param(ConstantsSubstation.SUBSTATION_NAME_SPELL) String table_end_spell);
	/**
	 * 根据手机号查询管理员详细信息 未分配回收亭时使用
	 * @Title: selectLogoInInfoByMobile 
	 * @param mobile
	 * @return    设定文件 
	 * @return List<Map<String,Object>>    返回类型 
	 * @throws 
	 * @author wanghancheng
	 * @date 2017年6月9日10:26:39
	 */
	List<Map<String,Object>> selectLoginInfoByMobile(String mobile);
	/**
	 * 根据手机号查询管理员权限信息
	* @Title: selectModuleInfoByMobile 
	* @param mobile
	* @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws 
	* @author wanghancheng
	* @date 2017年6月28日 上午10:36:40
	 */
	List<Map<String,Object>> selectModuleInfoByMobile(String mobile);
	/**
	 * 根据用户id查询角色id
	* @Title: selectRoleIdById 
	* @param adminId
	* @return    设定文件 
	* @return Integer    返回类型 
	* @throws 
	* @author wanghancheng
	* @date 2017年6月29日 上午11:56:16
	 */
	Integer selectRoleIdById(Integer adminId);

	/**
	 * 
	 * @Title: selectByIdAndHouseId 
	 * @Description: 查询服务亭下的管理员 
	 * @param parseInt
	 * @param houseId
	 * @return    设定文件 
	 * HlHouseAdmin    返回类型 
	 * @throws 
	 * @author tianyunyun
	 * @date 2017年9月1日 上午10:14:25
	 */
	HlHouseAdmin selectByIdAndHouseId(@Param("adminUserId") Integer adminUserId, @Param("houseId") Integer houseId, @Param(ConstantsSubstation.SUBSTATION_NAME_SPELL) String spell);
	
	/**
	 * 
	 * @Title: getHouseLeaderId 
	 * @Description: 获得此服务亭亭长id
	 * @param houseId 服务亭id
	 * @param hSSpell 
 	 * @return Integer
	 * @throws 
	 * @author liuyang
	 * @date 2017年12月19日16:44:05
	 */
	Integer getHouseLeaderId(@Param("houseId") Integer houseId, @Param(ConstantsSubstation.SUBSTATION_NAME_SPELL) String hSSpell);

}