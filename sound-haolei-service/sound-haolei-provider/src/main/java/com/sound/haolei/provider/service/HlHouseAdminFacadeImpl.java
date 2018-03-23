package com.sound.haolei.provider.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.sound.haolei.constants.ConstantsSubstation;
import com.sound.haolei.facade.HlHouseAdminFacade;
import com.sound.haolei.facade.HlSubstationFacade;
import com.sound.haolei.model.HlHouseAdmin;
import com.sound.haolei.model.HlHouseMiddleHouseAdmin;
import com.sound.haolei.model.HlSubstation;
import com.sound.haolei.provider.dao.HlHouseAdminMapper;
import com.sound.haolei.provider.dao.HlHouseMapper;
import com.sound.haolei.provider.dao.HlHouseMiddleHouseAdminMapper;
import com.sound.haolei.utils.CheckUtil;
import com.sound.haolei.utils.ConversionMd5;

/**
 * @author liuyang
 */
@Service(
		version = "1.0.0",
		application = "${dubbo.application.id}",
		protocol = "${dubbo.protocol.id}",
		registry = "${dubbo.registry.id}"
)
public class HlHouseAdminFacadeImpl extends BaseFacadeImpl<HlHouseAdmin, HlHouseAdminMapper>
		implements HlHouseAdminFacade {
	
	@Autowired
	private HlHouseAdminMapper hlHouseAdminMapper;
	@Autowired
	private HlHouseMiddleHouseAdminMapper middleMapper;
	@Autowired
	private HlHouseMapper hlHouseMapper;
	@Autowired
	private HlSubstationFacade hlSubstationFacade;
	@Autowired
	private HlHouseMiddleHouseAdminMapper hlHouseMiddleHouseAdminMapper;
	
	@Override
	public HlHouseAdminMapper getMapper() throws Exception {
		return this.hlHouseAdminMapper;
	}



    
    /**
     * 获取管理员权限信息
    * @Title: getModuleInfo 
    * @param mobile
    * @return    设定文件 
    * @return List<Map<String,Object>>    返回类型 
    * @throws 
    * @author wanghancheng
    * @date 2017年6月28日 上午11:05:22
     */
    private List<Map<String,Object>> getModuleInfo(String mobile){
    	List<Map<String, Object>>  moduleData = hlHouseAdminMapper.selectModuleInfoByMobile(mobile);
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	// 未查询到权限信息
    	if(CollectionUtils.isEmpty(moduleData)){
    		return new ArrayList<Map<String,Object>>();
    	}
    	for(Map<String, Object> module : moduleData){
    		// 权限类型，0：菜单权限，1：功能权限
    		Integer moduleType = (Integer) module.get("moduleType");
    		// 权限id
    		Long moduleId = (Long) module.get("moduleId");
    		// 权限名称
    		String moduleName = (String) module.get("moduleName");
    		if(moduleType == 0){
    			Map<String,Object> pmap = new HashMap<String,Object>();
    			List<Map<String,Object>> clist = new ArrayList<Map<String,Object>>();
    			for(Map<String, Object> cmodule : moduleData){
    				// 权限类型，0：菜单权限，1：功能权限
    	    		Integer cType = (Integer) cmodule.get("moduleType");
    				// 权限id
    				Long cmId = (Long) cmodule.get("moduleId");
		    		// 权限名称
		    		String cmName = (String) cmodule.get("moduleName");
		    		// 权限父id
		    		Long pid0 = (Long) cmodule.get("modulePid0");
    				if(cType == 1 && pid0 == moduleId){
    					Map<String,Object> cmap = new HashMap<String,Object>();
    					cmap.put("name", cmName);
    					cmap.put("id", cmId);
    					clist.add(cmap);
    				}
    			}
    			pmap.put("id", moduleId);
    			pmap.put("name", moduleName);
    			pmap.put("children", clist);
    			list.add(pmap);
    		}
    	}
    	return list;
    }
    
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
	@Override
	public Map<String, Object> addHousepOfAdmin(int adminId, Integer[] mids) {
		if(ArrayUtils.isEmpty(mids)){
			return getFailRtn("请选择服务亭");
		}
		try {
			//1.判断adminId是否存在
			HlHouseAdmin admin = hlHouseAdminMapper.selectByPrimaryKey(adminId,"");
			if(admin == null||admin.getId() == null){
				return getFailRtn("管理员不存在");
			}
			String substationSpell = "bengbu";
			HlSubstation substation = hlSubstationFacade.selectById(admin.getSubstationId());
			if(substation != null){
				substationSpell = substation.getSpell();
			}
			//2.将该adminId对应的回收机全部删除
			middleMapper.batchDeleteHouseWithSubstation(adminId, substationSpell);
			//3.给管理员添加新回收机
			List<HlHouseMiddleHouseAdmin> list = new ArrayList<>();
			for (Integer houseId : mids) {
				HlHouseMiddleHouseAdmin ha = new HlHouseMiddleHouseAdmin();
				ha.setAdminId(adminId);
				ha.setHouseId(houseId);
				list.add(ha);
			}
			middleMapper.batchAddHouseAdminWithSubstation(list, substationSpell);
		} catch (Exception e) {
			e.printStackTrace();
			return getFailRtn("保存失败");
		}
		return getSussRtn(null, "保存成功");
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
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> updateAdminStatus(int adminId,int status) {
		try {
			// 状态：0 正常 1 冻结
			HlHouseAdmin admin = new HlHouseAdmin();
			admin.setId(adminId);
			admin.setStatus((byte)status);
			HlHouseAdmin hlHouseAdmin = hlHouseAdminMapper.selectByPrimaryKey(adminId, "");
			if(null == hlHouseAdmin){
				return getFailRtn("没有此管理员");
			}
			int num = hlHouseAdminMapper.updateByPrimaryKeySelective(admin);
			if(num == 1){
				return getSussRtn(null);
			}
			return getFailRtn("修改失败");
		} catch (Exception e) {
			e.printStackTrace();
			return getFailRtn("修改失败");
		}
	}

	/**
	 * 新增或修改管理员
	 * @param houseId 服务亭id
	 * @param houseAdmin 服务亭管理员
	 * @param sell 分站全拼
	 * @return
	 * @throws Exception
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map<String, Object> saveOrUpdate(String houseId,HlHouseAdmin houseAdmin,String sell) throws Exception {
		Map<String, Object> data = new HashMap<>(16);
		if(!CheckUtil.isEmpty(houseAdmin.getMobile())){
			HlHouseAdmin hm = hlHouseAdminMapper.selectByMobile(houseAdmin.getMobile());
			//已存在
			if(null != hm){
				return getFailRtn("该手机号已注册管理员");
			}
		}
		if(houseAdmin.getId() != null && houseAdmin.getId() > 0){
			houseAdmin.setLtime( new Date());
			if(1 == houseAdmin.getHouseLeader()){
				if(!CheckUtil.isEmpty(houseId)){
					Integer id = hlHouseAdminMapper.getHouseLeaderId(Integer.parseInt(houseId),sell);
					//如果此人不是此服务亭亭长，将原亭长亭长状态置为0，
					if(!CheckUtil.isEmpty(id) && !id.equals(houseAdmin.getId())){
						HlHouseAdmin obj = new HlHouseAdmin();
						obj.setId(id);
						obj.setHouseLeader(new Byte("0"));
						hlHouseAdminMapper.updateByPrimaryKeySelective(obj);
					}
				}
			}
			hlHouseAdminMapper.updateByPrimaryKeySelective(houseAdmin);
			if(!CheckUtil.isEmpty( houseId )){
					hlHouseMiddleHouseAdminMapper.batchDeleteHouseWithSubstation(houseAdmin.getId(),sell);
					HlHouseMiddleHouseAdmin admin = new HlHouseMiddleHouseAdmin();
					admin.setAdminId(houseAdmin.getId());
					admin.setHouseId(Integer.parseInt(houseId));
					BeanUtils.setProperty(admin, ConstantsSubstation.SUBSTATION_NAME_SPELL,sell);
					hlHouseMiddleHouseAdminMapper.insertSelective(admin);

					if(!CheckUtil.isEmpty(admin.getId())){
						data.put("hlHouseMiddleHouseAdminId",admin.getId());
					}
			}
		}else{
			houseAdmin.setCtime( new Date());
			houseAdmin.setLtime( new Date());
			String passWord =  ConversionMd5.toMd5("sound" + houseAdmin.getPasswd() + "group");
			houseAdmin.setPasswd(passWord);
			if(houseAdmin.getHouseLeader() == 1){
				if(!CheckUtil.isEmpty(houseId)){
					Integer id = hlHouseAdminMapper.getHouseLeaderId(Integer.parseInt(houseId),sell);
					//如果此服务亭已有亭长，将原亭长亭长状态置为0，
					if(!CheckUtil.isEmpty(id)){
						HlHouseAdmin obj = new HlHouseAdmin();
						obj.setId(id);
						obj.setHouseLeader(new Byte("0"));
						hlHouseAdminMapper.updateByPrimaryKeySelective(obj);
					}
				}
			}
			hlHouseAdminMapper.insertSelective(houseAdmin);
			Integer houseAdminId = houseAdmin.getId();
			if(!CheckUtil.isEmpty(houseAdminId)){
				data.put("houseAdminId",houseAdminId);
			}
			if(!CheckUtil.isEmpty( houseId )){
				HlHouseMiddleHouseAdmin admin = new HlHouseMiddleHouseAdmin();
				admin.setAdminId(houseAdmin.getId());
				admin.setHouseId(Integer.parseInt(houseId));
				BeanUtils.setProperty(admin, ConstantsSubstation.SUBSTATION_NAME_SPELL, sell);
				hlHouseMiddleHouseAdminMapper.insertSelective(admin);
				if(!CheckUtil.isEmpty(admin.getId())){
					data.put("hlHouseMiddleHouseAdminId",admin.getId());
				}
			}
		}
		 return  getSussRtn(data);
	}

	
	/**
	 * 判断此服务亭是否有亭长
	 * @param houseId 服务亭id
 	 * @return Integer  返回0是指此服务亭没有亭长或者此人不是此服务亭亭长，1是指此服务亭已有亭长，2是指此人是此服务亭亭长
	 * @throws 
	 * @author liuyang
	 * @date 2017年12月19日16:44:05
	 */
	@Override
	public Integer isHouseLeader(Integer adminId, Integer houseId,String hSSpell) {
		//${substationNameSpell}
		Integer houseLeaderId = hlHouseAdminMapper.getHouseLeaderId(houseId,hSSpell);
		Integer msg = 0;
		if(null != houseLeaderId && !"".equals(houseLeaderId)){
			msg = 1;
			/**
			 * 如果此人是此服务亭亭长 msg=2
			 */
			if(null != adminId && !"".equals(adminId)){
				if(adminId.intValue() == houseLeaderId.intValue()){
					msg = 2;
				}
			}
		}
		return msg;
	}

	/**
	 * 通过id查询服务亭管理员信息
	 * @param id
	 * @return
	 */
	@Override
	public HlHouseAdmin selectById(Integer id) {
		HlHouseAdmin hlHouseAdmin = hlHouseAdminMapper.selectByPrimaryKey(id,"");
		return hlHouseAdmin;
	}
}
