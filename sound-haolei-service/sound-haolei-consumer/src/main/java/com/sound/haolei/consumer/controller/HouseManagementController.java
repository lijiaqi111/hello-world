package com.sound.haolei.consumer.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sound.haolei.constants.ConstantsSubstation;
import com.sound.haolei.facade.HlHouseAdminFacade;
import com.sound.haolei.facade.HlHouseFacade;
import com.sound.haolei.facade.HlHouseSubstationFacade;
import com.sound.haolei.facade.HlSubstationFacade;
import com.sound.haolei.facade.MapCityFacade;
import com.sound.haolei.facade.MapCountryFacade;
import com.sound.haolei.facade.MapProvinceFacade;
import com.sound.haolei.model.HlHouse;
import com.sound.haolei.utils.CheckUtil;


/**
 * 服务亭管理
 * @author liuyang
 * @date 2018年3月13日10:39:12 
 *  
 */
@RestController
@RequestMapping("/houseManagement")
public class HouseManagementController extends BaseController{

    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}")
    private MapProvinceFacade provinceFacade;

    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}")
    private MapCityFacade cityFacade;

    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}")
    private MapCountryFacade countryFacade;

    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}")
    private HlSubstationFacade hlSubstationFacade;

    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}")
    private HlHouseFacade hlHouseFacade;

    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}")
    private HlHouseSubstationFacade hlHouseSubstationFacade;

    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}")
    private HlHouseAdminFacade hlHouseAdminFacade;

    public HouseManagementController() {
    }


    /**
     * 服务亭管理新增或更新接口
     * @param wechatOfficialAccountsId 公众号id
     * @param id 服务亭id 新增时不填，编辑时必填
     * @param nickname 昵称
     * @param letter 昵称首字母
     * @param status 状态 0 禁用 1启用
     * @param provinceId 所在省份id
     * @param cityId 所在城市id
     * @param countyId 所在县id
     * @param area 所在小区
     * @param address 详细地址
     * @param potX 设备安置点经度
     * @param potY 设备安置点纬度
     * @param description 描述信息
     * @param startTime 开始营业时间
     * @param endTime 结束营业时间
     * @return Map
     */
    @ResponseBody
    @RequestMapping(value = "/saveorupdate", method = RequestMethod.POST)
    public Map<String,Object> saveOrUpdate(String wechatOfficialAccountsId,Integer id,String nickname,String letter,Integer status,Integer provinceId,Integer cityId,Integer countyId,
                                           String area,String address,String potX,String potY,String description,String startTime,String endTime){
        if(CheckUtil.isEmpty(wechatOfficialAccountsId)){
            return  getFailRtn("微信公众号不能为空");
        }
        HlHouse hlHouse = new HlHouse();
        Map<String,Object> paramMap = hlSubstationFacade.getHsIdAndHsSpellByWechatOfficialAccountsId(wechatOfficialAccountsId);
        if(null == paramMap){
            return  getFailRtn("没有与此微信公众号对应的分站");
        }
        paramMap.put(ConstantsSubstation.SUBSTATION_NAME_SPELL,"_"+paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());
        hlHouse.setSubstationNameSpell(paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());

        if(!CheckUtil.isEmpty(nickname)){
            hlHouse.setNickname(nickname.trim());
        }else {
            return  getFailRtn("昵称不能为空");
        }

        if(!CheckUtil.isEmpty(letter)){
            hlHouse.setLetter(letter.trim().toUpperCase());
        }else {
            return  getFailRtn("昵称首字母不能为空");
        }

        if(!CheckUtil.isEmpty(status)){
            hlHouse.setStatus(status);
        }else {
            return  getFailRtn("状态不能为空");
        }

        if(!CheckUtil.isEmpty(provinceId)){
            hlHouse.setProvinceId(provinceId);
        }else {
            return  getFailRtn("所在省份不能为空");
        }

        if(!CheckUtil.isEmpty(cityId)){
            hlHouse.setCityId(cityId);
        }else {
            return  getFailRtn("所在城市不能为空");
        }

        if(!CheckUtil.isEmpty(countyId)){
            hlHouse.setCountyId(countyId);
        }else {
            return  getFailRtn("所在县不能为空");
        }

        if(!CheckUtil.isEmpty(address)){
            hlHouse.setAddress(address);
        }else {
            return  getFailRtn("详细地址不能为空");
        }

        if(!CheckUtil.isEmpty(area)){
            hlHouse.setArea(area);
        }else {
            return  getFailRtn("所在小区不能为空");
        }

        if(!CheckUtil.isEmpty(potX)){
            hlHouse.setPotX(potX);
        }else {
            return  getFailRtn("设备安置点经度不能为空");
        }

        if(!CheckUtil.isEmpty(potY)){
            hlHouse.setPotY(potY);
        }else {
            return  getFailRtn("设备安置点纬度不能为空");
        }

        if(!CheckUtil.isEmpty(description)){
            hlHouse.setDescription(description);
        }else {
            return  getFailRtn("描述信息不能为空");
        }
        try {
            if(!CheckUtil.isEmpty(startTime)){
                Date startDate = new SimpleDateFormat("HH:mm:ss").parse(startTime);
                hlHouse.setStartTime(startDate);
            }else {
                return  getFailRtn("开始营业时间不能为空");
            }
            if(!CheckUtil.isEmpty(endTime)){
                Date endDate = new SimpleDateFormat("HH:mm:ss").parse(endTime);
                hlHouse.setEndTime(endDate);
            }else {
                return  getFailRtn("结束营业时间不能为空");
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return  getFailRtn("时间格式不正确");
        }
        //因为php管理员用户和好嘞的不一致，暂时用分站id代替
        paramMap.put("cuid", paramMap.get(ConstantsSubstation.SUBSTATION_ID));
        Map<String,Object> data = new HashMap<>(16);
        try {
            if(!CheckUtil.isEmpty(id)){
                hlHouse.setId(id);
                hlHouse.setLtime(new Date());
                hlHouse.setLuid(Integer.parseInt(paramMap.get(ConstantsSubstation.SUBSTATION_ID).toString()));
                hlHouseFacade.update(hlHouse);
            }else {
                hlHouse.setCtime(new Date());
                hlHouse.setLtime(new Date());
                //记录创建人
                hlHouse.setCuid(Integer.parseInt(paramMap.get(ConstantsSubstation.SUBSTATION_ID).toString()));
                hlHouse.setLuid(Integer.parseInt(paramMap.get(ConstantsSubstation.SUBSTATION_ID).toString()));

                //先往中间表插入，然后取中间表的id给house插入
                Integer integer = hlHouseSubstationFacade.saveSubAndHouse(hlHouse, Integer.parseInt(paramMap.get(ConstantsSubstation.SUBSTATION_ID).toString()));
                if (!CheckUtil.isEmpty(integer)){
                    data.put("houseId",integer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getFailRtn("保存数据出错,请与管理人员联系!");
        }

        return getSuccessRtn(data);
    }

    /**
     * 通过wechatOfficialAccountsId和服务亭Id获取服务亭信息
     * @param wechatOfficialAccountsId 微信公众号id
     * @param id 服务亭id
     * @return Map<String,Object>
     * @author liuyang
     */
    @ResponseBody
    @RequestMapping(value = "/getInfo")
    public Map<String,Object> getInfo(String wechatOfficialAccountsId,Integer id){
        if(CheckUtil.isEmpty(wechatOfficialAccountsId)){
            return  getFailRtn("微信公众号不能为空");
        }
        if(CheckUtil.isEmpty(id)){
            return  getFailRtn("服务亭id不能为空");
        }
        Map<String,Object> paramMap = hlSubstationFacade.getHsIdAndHsSpellByWechatOfficialAccountsId(wechatOfficialAccountsId);
        if(null == paramMap){
            return  getFailRtn("没有与此微信公众号对应的分站");
        }
        paramMap.put(ConstantsSubstation.SUBSTATION_NAME_SPELL,"_"+paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());
        HlHouse hlHouse = hlHouseFacade.selectByIdAndSubstationNameSpell(id,paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());
        return getSuccessRtn(hlHouse);
    }

    /**
     * 加载服务亭信息数据
     * @param wechatOfficialAccountsId 公众号id
     * @param id 服务亭id 新增时不填，编辑时必填
     * @param nickname 昵称
     * @param orderStr 排序
     * @param provinceId 所在省份id
     * @param cityId 所在城市id
     * @param countyId 所在县id
     * @param cSartTime 创建开始时间
     * @param cEndTime 创建结束时间
     * @param lSartTime 修改开始时间
     * @param lEndTime 修改结束时间
     * @param page 当前页
     * @param size 每页显示数
     * @return Map
     */
    @RequestMapping(value="houseList")
    @ResponseBody
    public Map<String,Object> lianYunMachineList(String wechatOfficialAccountsId,String nickname, String orderStr,String provinceId,String cityId,
                                                 String countryId,String cSartTime,String cEndTime,String lSartTime,String lEndTime,Integer page,Integer size){
        if(CheckUtil.isEmpty(wechatOfficialAccountsId)){
            return getFailRtn("微信公众号不能为空");
        }
        Map<String,Object> paramMap = hlSubstationFacade.getHsIdAndHsSpellByWechatOfficialAccountsId(wechatOfficialAccountsId.trim());
        if(null == paramMap){
            return getFailRtn("没有与此微信公众号对应的分站");
        }
        paramMap.put(ConstantsSubstation.SUBSTATION_NAME_SPELL,"_"+paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());

        if(!CheckUtil.isEmpty(orderStr)){
            paramMap.put("orderStr", orderStr.trim());
        }
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

        if(!CheckUtil.isEmpty(nickname)){
            paramMap.put("nickname", nickname.trim());
        }

        if(!CheckUtil.isEmpty(provinceId) && !provinceId.equals("-1")){
            paramMap.put("provinceId", provinceId);
        }
        if(!CheckUtil.isEmpty(countryId) && !countryId.equals("-1")){
            paramMap.put("countryId", countryId);
        }
        if(!CheckUtil.isEmpty(cityId) && !cityId.equals("-1")){
            paramMap.put("cityId", cityId);
        }

        paramMap.put("page", page != null ? page : 1);
        paramMap.put("size", size != null ? size: 20);

        try {
            return hlHouseFacade.queryPageResult(paramMap);
           // return getPageResult(hlHouseFacade, paramMap);
        } catch (Exception e) {
            e.printStackTrace();
            return getFailRtn("获取数据失败");
        }
    }

    /**
     * 服务亭状态修改,即启用禁用接口
     * @Title: updateHouseStatus
     * @Description: 服务亭状态修改,即启用禁用接口
     * @param wechatOfficialAccountsId 公众号id
     * @param houseId 服务亭Id
     * @param status 状态：0 禁用，1 启用
     * @return Map<String,Object>    返回类型
     * @throws
     * @author liuyang
     * @date 2018年3月14日15:29:49
     */
    @RequestMapping(value="updatehousestatus", produces="application/json; charset=utf-8")
    @ResponseBody
    public Map<String,Object> updateHouseStatus(String wechatOfficialAccountsId, Integer houseId, Integer status){
        if(CheckUtil.isEmpty(wechatOfficialAccountsId)){
            return getFailRtn("微信公众号不能为空");
        }
        Map<String,Object> paramMap = hlSubstationFacade.getHsIdAndHsSpellByWechatOfficialAccountsId(wechatOfficialAccountsId.trim());
        if(null == paramMap){
            return getFailRtn("没有与此微信公众号对应的分站");
        }
        paramMap.put(ConstantsSubstation.SUBSTATION_NAME_SPELL,"_"+paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());

        if(CheckUtil.isEmpty(houseId)){
            return getFailRtn("服务亭Id不能为空");
        }else {
            paramMap.put("houseId",houseId);
        }
        if(CheckUtil.isEmpty(status)){
            return getFailRtn("管理员启用禁用状态不能为空");
        }else {
            paramMap.put("status",status);
        }
        return  hlHouseFacade.updateHouseStatus(paramMap);
    }
}

