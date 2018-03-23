package com.sound.haolei.consumer.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sound.haolei.constants.ConstantsSubstation;
import com.sound.haolei.constants.DefaultValueConstants;
import com.sound.haolei.facade.HlHouseAdminFacade;
import com.sound.haolei.facade.HlHouseAdminRoleFacade;
import com.sound.haolei.facade.HlSubstationFacade;
import com.sound.haolei.model.HlHouseAdmin;
import com.sound.haolei.model.HlHouseAdminRole;
import com.sound.haolei.utils.CheckUtil;
import com.sound.haolei.utils.ConversionMd5;

/**
 *  站内信管理
* @ClassName: MailController 
* @author liuyang
* @date 2018年3月13日
*  
 */
@Controller
@RequestMapping("/houseadminuser")
public class HouseAdminUserController extends BaseController {
	
	@Reference(version = "1.0.0",
			application = "${dubbo.application.id}")
	private HlHouseAdminFacade hlHouseAdminFacade;

	@Reference(version = "1.0.0",
			application = "${dubbo.application.id}")
	private HlSubstationFacade hlSubstationFacade;

	@Reference(version = "1.0.0",
			application = "${dubbo.application.id}")
	private HlHouseAdminRoleFacade hlHouseAdminRoleFacade;
	

	/**
	 * 获取服务亭管理员详情
	* @Title: getInfo 
	* @param  wechatOfficialAccountsId
	* @param  id
	* @return Map    返回类型
	* @author liuyang
	* @date 2018年3月13日18:09:19
	 */
	@ResponseBody
	@RequestMapping(value = "/getInfo")
	public Map<String,Object> getInfo(String  wechatOfficialAccountsId,Integer id) {
		if(CheckUtil.isEmpty(wechatOfficialAccountsId)){
			return  getFailRtn("微信公众号不能为空");
		}
		if(CheckUtil.isEmpty(id)){
			return  getFailRtn("服务亭管理员id不能为空");
		}
		Map<String,Object> paramMap = hlSubstationFacade.getHsIdAndHsSpellByWechatOfficialAccountsId(wechatOfficialAccountsId);
		if(null == paramMap){
			return  getFailRtn("没有与此微信公众号对应的分站");
		}
		paramMap.put(ConstantsSubstation.SUBSTATION_NAME_SPELL,"_"+paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());
		HlHouseAdmin hlHouseAdmin = null;
		try {
			hlHouseAdmin = hlHouseAdminFacade.selectById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return getFailRtn("获取数据失败");
		}
		Map<String,Object> data = new HashMap<String,Object>(16);
		data.put("hlHouseAdmin",hlHouseAdmin);
		try {
			List<HlHouseAdminRole> houseAdminRoleList = hlHouseAdminRoleFacade.selectAll();
			data.put("houseAdminRoleList",houseAdminRoleList);
		} catch (Exception e) {
			e.printStackTrace();
			return getFailRtn("获取数据失败");
		}
		return getSuccessRtn(data);
	}

	/**
	 * 服务亭管理员新增或更新接口
	 * @param wechatOfficialAccountsId 公众号id
	 * @param id 管理员id，即表hl_house_admin id
	 * @param mobile 管理员手机号
	 * @param identityCard 身份证号
	 * @param bankCardNo 银行卡号
	 * @param bankCardName 银行卡开户行名称
	 * @param nickname 管理员昵称
	 * @param houseLeader 是否服务亭亭长 0否 1是
	 * @param passwd 管理员密码
	 * @param status 状态：0 正常 1 冻结
	 * @param houseAdminRoleId 管理端管理员角色id
	 * @param address 管理员现住址
	 * @param houseId 服务亭id
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value = "/saveorupdate", method = RequestMethod.POST)
	public Map<String,Object> saveorupdate(String wechatOfficialAccountsId,Integer id,String mobile,String identityCard,String bankCardNo,String bankCardName,String nickname,
									 Integer houseLeader,String passwd,Integer status,Integer houseAdminRoleId,String address,String houseId) {
		if(CheckUtil.isEmpty(wechatOfficialAccountsId)){
			return  getFailRtn("微信公众号不能为空");
		}
		Map<String,Object> paramMap = hlSubstationFacade.getHsIdAndHsSpellByWechatOfficialAccountsId(wechatOfficialAccountsId);
		if(null == paramMap){
			return  getFailRtn("没有与此微信公众号对应的分站");
		}
		paramMap.put(ConstantsSubstation.SUBSTATION_NAME_SPELL,"_"+paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL));
		String sell = paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString();
		HlHouseAdmin user = new HlHouseAdmin();
		Map<String, Object> rtn = new HashMap<>(16);
		if(!CheckUtil.isEmpty(mobile)){
			user.setMobile(mobile.trim());
		}else {
			return getFailRtn("手机号不能为空");
		}
		if(!CheckUtil.isEmpty(id)){
			user.setId(id);
		}
		if(!CheckUtil.isEmpty(houseLeader)){
			user.setHouseLeader(houseLeader.byteValue());
			if (1 == houseLeader.intValue()){
				if(!CheckUtil.isEmpty(identityCard)){
					user.setIdentityCard(identityCard);
				}else {
					return getFailRtn("身份证号不能为空");
				}
				if(!CheckUtil.isEmpty(bankCardNo)){
					user.setBankCardNo(bankCardNo);
				}else {
					return getFailRtn("银行卡号不能为空");
				}
				if(!CheckUtil.isEmpty(bankCardName)){
					user.setBankCardName(bankCardName);
				}else {
					return getFailRtn("银行名称不能为空");
				}
				if(!CheckUtil.isEmpty(address)){
					user.setAddress(address);
				}
			}
		}else {
			return getFailRtn("是否为服务亭亭长不能为空");
		}

		if(!CheckUtil.isEmpty(nickname)){
			user.setNickname(nickname);
		}else {
			return getFailRtn("管理员昵称不能为空");
		}
		if(!CheckUtil.isEmpty(passwd)){
			user.setPasswd(passwd);
		}else {
			return getFailRtn("登录密码不能为空");
		}
		if(!CheckUtil.isEmpty(status)){
			user.setStatus(status.byteValue());
		}else {
			return getFailRtn("状态不能为空");
		}
		if(!CheckUtil.isEmpty(houseAdminRoleId)){
			user.setHouseAdminRoleId(houseAdminRoleId);
		}else {
			Integer roleId = hlHouseAdminRoleFacade.getOneId();
			user.setHouseAdminRoleId(roleId);
		}
		if(CheckUtil.isEmpty(houseId)){
			return getFailRtn("所属服务亭不能为空");
		}
		try {
			rtn = hlHouseAdminFacade.saveOrUpdate(houseId,user,sell);
		} catch (Exception e) {
			e.printStackTrace();
			return getFailRtn("保存数据出错请与管理员联系");
		}
		return rtn;
	}
	
	
	@RequestMapping(value = "/updatePass", method = RequestMethod.POST)
	public ModelAndView updatePass(@Valid @ModelAttribute("user") HlHouseAdmin user,ModelAndView modelAndView) {
		String pass = ConversionMd5.toMd5("sound"+user.getPasswd()+"group");
		user.setPasswd(pass);
		try {
				hlHouseAdminFacade.update(user);
				modelAndView.setViewName("redirect:/houseadminuser/index");
			} catch (Exception e) {
				modelAndView.addObject("errMsg", "修改密码出错,请与技术人员联系");
				modelAndView.setViewName("/admin/error/error");
				e.printStackTrace();
			}
		return modelAndView;
	}
	
	/**
	 * 批量删除服务亭管理员
	* @Title: ajaxBatchDelete 
	* @param @param machineIds    设定文件 
	* @return void    返回类型 
	* @throws 
	* @author wanghancheng
	* @date 2017年5月1日 下午9:04:47
	 */
	@RequestMapping(value="/ajaxDelete",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> ajaxBatchDelete(String[] uids){
		List<String> list = Arrays.asList(uids);
		List<Integer> ids = new ArrayList<>();
		
		if(CheckUtil.isEmpty(list)){
			return getFailRtn("参数不能为空");
		}
		for (String idstr : list) {
			Integer id = -1;
			if (idstr != null && !"".equals(idstr)) {
				id = Integer.parseInt(idstr);
				try {
					HlHouseAdmin admin = (HlHouseAdmin) hlHouseAdminFacade.selectById(id);
					if (null != admin ) {
						ids.add(id);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (ids != null && ids.size() > 0) {
			try {
				hlHouseAdminFacade.batchDelete(ids);
				return getSuccessRtn("","删除成功");
			} catch (Exception e) {
				e.printStackTrace();
				return getFailRtn("删除失败");
			}
		}else{
			return getFailRtn("数据库中不存在所选择数据");
		}
	}

	/**
	 * 加载回收亭管理员列表
	 * @param wechatOfficialAccountsId 公众号id
	 * @param mobile 管理员手机号
	 * @param orderStr 排序
	 * @param cSartTime 创建开始时间
	 * @param cEndTime 创建结束时间
	 * @param lSartTime 修改开始时间
	 * @param lEndTime 修改结束时间
	 * @param nickname 管理员昵称
	 * @param status 状态：0 正常 1 冻结
	 * @param page 页数
	 * @param size 每页显示数
	 * @return Map
	 */
	@RequestMapping(value="houseAdminUserList")
	@ResponseBody
	public Map<String,Object> ajaxDataList(String wechatOfficialAccountsId, String cSartTime,String cEndTime,String lSartTime,String lEndTime,String orderStr,
			String mobile,String nickname,Integer page,Integer size,Integer status){
		if(CheckUtil.isEmpty(wechatOfficialAccountsId)){
			return  getFailRtn("微信公众号不能为空");
		}
		// 返回结果对象
		Map<String,Object> retMap = null;
		Map<String,Object> paramMap = hlSubstationFacade.getHsIdAndHsSpellByWechatOfficialAccountsId(wechatOfficialAccountsId);
		if(null == paramMap){
			return  getFailRtn("没有与此微信公众号对应的分站");
		}
		paramMap.put(ConstantsSubstation.SUBSTATION_NAME_SPELL,"_"+paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL));
		String hSId = paramMap.get(ConstantsSubstation.SUBSTATION_ID).toString();

		if( !CheckUtil.isEmpty(hSId)){
			if( hSId.equals(DefaultValueConstants.DEFAULT_SUBSTATION_ID) ){
				hSId = " substation_id not in( select id from hl_substation  where status = 1 and id <> "+DefaultValueConstants.DEFAULT_SUBSTATION_ID+") ";
			}else{
				hSId = " substation_id = "+hSId;
			}
			paramMap.put("hSId", hSId);
		}
		paramMap.put("orderStr",orderStr);
		if(!CheckUtil.isEmpty(cSartTime)){
			paramMap.put("cSartTime", cSartTime.trim());
		}
		if(!CheckUtil.isEmpty(cEndTime)){
			paramMap.put("cEndTime", cEndTime.trim());
		}
		if(!CheckUtil.isEmpty(lSartTime)){
			paramMap.put("lSartTime", lSartTime.trim());
		}
		if(!CheckUtil.isEmpty(lEndTime)){
			paramMap.put("lEndTime", lEndTime.trim());
		}
		if(!CheckUtil.isEmpty(mobile)){
			paramMap.put("mobile", mobile.trim());
		}
		if(!CheckUtil.isEmpty(nickname)){
			paramMap.put("nickname", nickname.trim());
		}

		if(!CheckUtil.isEmpty(status)){
			paramMap.put("status", status);
		}

		paramMap.put("page", page != null ? page : 1);
		paramMap.put("size", size != null ? size: 20);
		// 调用服务，查询结果
		try {
			retMap = getPageResult(hlHouseAdminFacade, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retMap;
	}
	
	/**
	 * 回收亭管理员添加回收亭
	 * @Title: addHousepOfAdmin 
	 * @param adminId 管理员Id
	 * @param mids 回收亭Id，多个用英文逗号分隔
	 * @return Map    返回类型 
	 * @throws 
	 * @author wangzhanguo
	 * @date 2017年5月24日 下午12:25:21
	 */
	@RequestMapping(value="addhousepofadmin", produces="application/json; charset=utf-8")
	@ResponseBody
	public Map<String,Object> addHousepOfAdmin(int adminId, Integer[] mids){
		return hlHouseAdminFacade.addHousepOfAdmin(adminId,mids);
	}
	
	/**
	 * 回收亭管理员状态修改,即启用禁用接口
	* @Title: updateAdminStatus 
	* @Description: 回收亭管理员状态修改,即启用禁用接口
	* @param wechatOfficialAccountsId 微信公众号id
	* @param adminId 管理员Id
	* @param status 状态值
	* @return Map<String,Object>    返回类型
	* @throws 
	* @author liuyang
	* @date 2018年3月14日15:29:49
	 */
	@RequestMapping(value="updateadminstatus", produces="application/json; charset=utf-8")
	@ResponseBody
	public Map<String,Object> updateAdminStatus(String wechatOfficialAccountsId,Integer adminId,Integer status){
		if(CheckUtil.isEmpty(wechatOfficialAccountsId)){
			return  getFailRtn("微信公众号不能为空");
		}
		Map<String,Object> paramMap = hlSubstationFacade.getHsIdAndHsSpellByWechatOfficialAccountsId(wechatOfficialAccountsId);
		if(null == paramMap){
			return  getFailRtn("没有与此微信公众号对应的分站");
		}
		if(CheckUtil.isEmpty(adminId)){
			return getFailRtn("管理员Id不能为空");
		}
		try {
			HlHouseAdmin hlHouseAdmin = hlHouseAdminFacade.selectById(adminId);
			if(null == hlHouseAdmin){
				return getFailRtn("没有此管理员");
			}
			Integer substationId = hlHouseAdmin.getSubstationId();
			Integer substationId2 = Integer.parseInt(paramMap.get(ConstantsSubstation.SUBSTATION_ID).toString());
			if(!substationId.equals(substationId2)){
				return getFailRtn("此人不是这个分站的管理员");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if(CheckUtil.isEmpty(status)){
			return getFailRtn("管理员启用禁用状态不能为空");
		}
		return hlHouseAdminFacade.updateAdminStatus(adminId,status);
	}
	
	/**
	 * 查询该服务亭是否有亭长
	 * @param wechatOfficialAccountsId 微信公众号id
	 * @param adminId 管理员Id
	 * @param status 状态值
	 * @return Integer   返回0是指此服务亭没有亭长或者此人不是此服务亭亭长，1是指此服务亭已有亭长，2是指此人是此服务亭亭长
	 * @author liuyang
	 * @date 2017年7月19日 下午4:29:19
	 */
	@RequestMapping(value="isHouseLeader", produces="application/json; charset=utf-8")
	@ResponseBody
	public Map<String,Object> isHouseLeader(String  wechatOfficialAccountsId,Integer houseId,Integer adminId){
		if(CheckUtil.isEmpty(wechatOfficialAccountsId)){
			return  getFailRtn("微信公众号不能为空");
		}
		if(CheckUtil.isEmpty(houseId)){
			return  getFailRtn("服务亭id不能为空");
		}
		if(CheckUtil.isEmpty(adminId)){
			return  getFailRtn("服务亭管理员id不能为空");
		}
		Map<String,Object> paramMap = hlSubstationFacade.getHsIdAndHsSpellByWechatOfficialAccountsId(wechatOfficialAccountsId);
		if(null == paramMap){
			return  getFailRtn("没有与此微信公众号对应的分站");
		}
		paramMap.put(ConstantsSubstation.SUBSTATION_NAME_SPELL,"_"+paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL));
		String hSSpell = paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString();
		Integer houseLeader = hlHouseAdminFacade.isHouseLeader(adminId, houseId, hSSpell);
		return getSuccessRtn(houseLeader);
	}
}
