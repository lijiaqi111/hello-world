package com.sound.haolei.provider.dao;

import com.sound.haolei.model.HlHouseAdminModule;
import com.sound.haolei.model.HlHouseAdminRole;

public interface HlHouseAdminRoleMapper extends IBaseMapper<HlHouseAdminRole>{
	/**
	 * 查询角色权限信息
	* @Title: selectRoleModuleByPrimaryKey 
	* @param id
	* @return    设定文件 
	* @return HlHouseAdminRole    返回类型 
	* @throws 
	* @author wanghancheng
	* @date 2017年6月27日 下午8:25:15
	 */
	HlHouseAdminRole selectRoleModuleByPrimaryKey(int id);
	/**
	 * 添加角色权限
	* @Title: insertrolemodule 
	* @param role
	* @param module
	* @return    设定文件 
	* @return int    返回类型 
	* @throws 
	* @author wanghancheng
	* @date 2017年6月27日 下午8:25:27
	 */
	int insertrolemodule(HlHouseAdminRole role, HlHouseAdminModule module);
	/**
	 * 删除角色权限中间表数据
	* @Title: deleteRoleModulesByRoleid 
	* @param rid
	* @return    设定文件 
	* @return int    返回类型 
	* @throws 
	* @author wanghancheng
	* @date 2017年6月27日 下午8:25:43
	 */
	int deleteRoleModulesByRoleid(int rid);
	/**
	 * 更新角色信息
	* @Title: updateRole 
	* @param role
	* @return    设定文件 
	* @return int    返回类型 
	* @throws 
	* @author wanghancheng
	* @date 2017年6月27日 下午8:25:58
	 */
	int updateRole(HlHouseAdminRole role);

	/**
	 * 获取一个服务亭管理员角色id
	 * @return
	 */
    Integer getOneId();
}