package com.sound.haolei.facade;

import java.util.Map;

import com.sound.haolei.exception.MyException;
import com.sound.haolei.model.HlHouseAdminRole;

/**
 * @author liuyang
 */
public interface HlHouseAdminRoleFacade extends BaseFacade<HlHouseAdminRole>{
	/**
	 * 查询管理员角色信息
	* @Title: selectRoleModuleByPrimaryKey 
	* @param id
	* @return    设定文件 
	* @return HlHouseAdminRole    返回类型 
	* @throws 
	* @author wanghancheng
	* @date 2017年6月27日 下午8:24:00
	 */
	public HlHouseAdminRole selectRoleModuleByPrimaryKey(int id);
	/**
	 * 添加角色权限
	* @Title: insertRoleAndModules 
	* @param role
	* @return
	* @throws MyException    设定文件 
	* @return int    返回类型 
	* @throws 
	* @author wanghancheng
	* @date 2017年6月27日 下午8:24:18
	 */
	public int insertRoleAndModules(HlHouseAdminRole role) throws MyException;
	
	/**
     * 删除中间表中此 rid 的值
     * @return
     */
    public int deleteRoleModulesByRoleid(int rid);
    
    /**
     * 更新 role 包括其中的 modules
     * @param role
     * @return
     */
    public int updateRoleModules(HlHouseAdminRole role) throws MyException;
    /**
     * 删除角色权限数据
    * @Title: deleteRoleModules 
    * @param roleId
    * @return    设定文件 
    * @return int    返回类型 
    * @throws 
    * @author wanghancheng
    * @date 2017年6月27日 下午8:24:44
     */
	public int deleteRoleModules(int roleId);

	/**
	 * 获取一个角色id
	 * @return
	 */
    Integer getOneId();
}
