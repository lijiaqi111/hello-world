package com.sound.haolei.provider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.sound.haolei.exception.MyException;
import com.sound.haolei.facade.HlHouseAdminRoleFacade;
import com.sound.haolei.model.HlHouseAdminModule;
import com.sound.haolei.model.HlHouseAdminRole;
import com.sound.haolei.provider.dao.HlHouseAdminMapper;
import com.sound.haolei.provider.dao.HlHouseAdminRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

@Service(
		version = "1.0.0",
		application = "${dubbo.application.id}",
		protocol = "${dubbo.protocol.id}",
		registry = "${dubbo.registry.id}"
)
public class HlHouseAdminRoleFacadeImpl extends BaseFacadeImpl<HlHouseAdminRole, HlHouseAdminRoleMapper>
				implements HlHouseAdminRoleFacade{
	
	@Autowired
	private HlHouseAdminRoleMapper hlHouseAdminRoleMapper;
	@Autowired
	private HlHouseAdminMapper hlHouseAdminMapper;

	@Override
	public HlHouseAdminRoleMapper getMapper() throws Exception {
		return hlHouseAdminRoleMapper;
	}

	@Override
	public HlHouseAdminRole selectRoleModuleByPrimaryKey(int id) {
		return hlHouseAdminRoleMapper.selectRoleModuleByPrimaryKey(id);
	}

	@Override
	public int insertRoleAndModules(HlHouseAdminRole role) throws MyException {
		try {
			hlHouseAdminRoleMapper.insertSelective(role);
		} catch (DuplicateKeyException e) {
			throw new MyException("名称【"+role.getName()+"】重复，请检查！");
		}
		
		//System.out.println("role is:"+role);
		List<HlHouseAdminModule> modules = role.getModules();
		for(HlHouseAdminModule module: modules){
			hlHouseAdminRoleMapper.insertrolemodule(role, module);
		}
		return 0;
	}
	/**
     * 删除中间表中此 rid 的值
     * @return
     */
	@Override
	public int deleteRoleModulesByRoleid(int rid) {
		return hlHouseAdminRoleMapper.deleteRoleModulesByRoleid(rid);
	}
	/**
     * 更新 role 包括其中的 modules
     * @param role
     * @return
     */
	@Override
	public int updateRoleModules(HlHouseAdminRole role) throws MyException {
		try {
			hlHouseAdminRoleMapper.updateRole(role);
		} catch (DuplicateKeyException e) {
			throw new MyException("名称【"+role.getName()+"】重复，请检查！");
		}
		
		List<HlHouseAdminModule> modules = role.getModules();
		for(HlHouseAdminModule module: modules){
			hlHouseAdminRoleMapper.insertrolemodule(role, module);
		}
		return 0;
	}

	@Override
	public int deleteRoleModules(int roleId) {
		hlHouseAdminRoleMapper.deleteRoleModulesByRoleid(roleId);
		hlHouseAdminRoleMapper.deleteByPrimaryKey(roleId,"");
		return 0;
	}

    @Override
    public Integer getOneId() {
        return hlHouseAdminRoleMapper.getOneId();
    }


}
