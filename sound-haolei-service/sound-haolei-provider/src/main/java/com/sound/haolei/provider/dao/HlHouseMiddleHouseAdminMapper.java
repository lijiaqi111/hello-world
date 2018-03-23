package com.sound.haolei.provider.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sound.haolei.constants.ConstantsSubstation;
import com.sound.haolei.model.HlHouseMiddleHouseAdmin;

/**
 * @author liuyang
 */
public interface HlHouseMiddleHouseAdminMapper extends IBaseMapper<HlHouseMiddleHouseAdmin> {

	// 批量删除管理员
	void batchDeleteUser(@Param("list") List<String> list, @Param("houseId") String houseId, @Param(ConstantsSubstation.SUBSTATION_NAME_SPELL) String spell);

	/**
	 * 删除管理员对应的回收亭
	 * 
	 * @param list
	 * @param adminId
	 */
	void batchDeleteHouse(@Param("adminId") Integer adminId, @Param(ConstantsSubstation.SUBSTATION_NAME_SPELL) String spell);

	/**
	 * 删除管理员对应的回收亭
	 * 
	 * @param list
	 * @param adminId
	 * @param substationSpell
	 */
	void batchDeleteHouseWithSubstation(@Param("adminId") Integer adminId, @Param("substationSpell") String substationSpell);

	/**
	 * 批量添加管理员对应的回收亭
	 * 
	 * @param list
	 * @param adminId
	 */
	void batchAddHouseAdmin(@Param("list") List<HlHouseMiddleHouseAdmin> list, @Param(ConstantsSubstation.SUBSTATION_NAME_SPELL) String spell);

	/**
	 * 批量添加管理员对应的回收亭
	 * 
	 * @param list
	 * @param adminId
	 * @param substationSpell
	 */
	void batchAddHouseAdminWithSubstation(@Param("list") List<HlHouseMiddleHouseAdmin> list, @Param("substationSpell") String substationSpell);

	/**
	 * 查询管理员管辖的回收亭
	 * 
	 * @Title: selectByAdminId
	 * @param adminId
	 *            管理员id
	 * @return 设定文件
	 * @return List<HlHouseMiddleHouseAdmin> 返回类型
	 * @throws @author
	 *             wanghancheng
	 * @date 2017年6月2日 上午11:29:52
	 */
	List<HlHouseMiddleHouseAdmin> selectByAdminId(@Param("adminId") Integer adminId, @Param(ConstantsSubstation.SUBSTATION_NAME_SPELL) String spell);
	/**
	 * 查询管理员管辖的回收亭
	* @Title: selectByAdminIdWithSubstation 
	* @param adminId
	* @param substationSpell
	* @return    设定文件 
	* @return List<HlHouseMiddleHouseAdmin>    返回类型 
	* @throws 
	* @author wanghancheng
	* @date 2017年7月11日 下午5:26:49
	 */
	List<HlHouseMiddleHouseAdmin> selectByAdminIdWithSubstation(@Param("adminId") Integer adminId, @Param("substationSpell") String substationSpell);
	
	/**
	 * 查询管理员管辖的回收亭
	 * 
	 * @Title: selectByAdminId
	 * @param adminId
	 *            管理员id
	 * @return 设定文件
	 * @return List<HlHouseMiddleHouseAdmin> 返回类型
	 * @throws @author
	 *             wangruwei
	 * @date 2017年6月2日 上午11:29:52
	 */
	List<HlHouseMiddleHouseAdmin> selectByAdminIdAndTableEnd(@Param(ConstantsSubstation.SUBSTATION_NAME_SPELL) String substationNameSpell, @Param("adminId") int parseInt);
}