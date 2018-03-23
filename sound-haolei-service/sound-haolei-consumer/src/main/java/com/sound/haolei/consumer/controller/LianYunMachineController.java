package com.sound.haolei.consumer.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.sound.haolei.dto.HsMachineLianyunDto;
import com.sound.haolei.model.MapCity;
import com.sound.haolei.model.MapCountry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sound.haolei.constants.ConstantsSubstation;
import com.sound.haolei.constants.ServiceCodeConstants;
import com.sound.haolei.facade.HlSubstationFacade;
import com.sound.haolei.facade.HsMachineLianyunFacade;
import com.sound.haolei.facade.MapCityFacade;
import com.sound.haolei.facade.MapCountryFacade;
import com.sound.haolei.facade.MapProvinceFacade;
import com.sound.haolei.model.HsMachineLianyun;
import com.sound.haolei.model.MapProvince;
import com.sound.haolei.utils.CheckUtil;


/**
 * 联运回收机管理
 * @author liuyang
 * @date 2018年3月5日 
 *  
 */
@RestController
@RequestMapping("/lianyunmachine")
public class LianYunMachineController extends BaseController{

    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}")
    private HsMachineLianyunFacade hsMachineLYFacade;

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

    private final String MACHINE_ERROR_CODE = "jqmerror";
    public LianYunMachineController() {
    }

    /**
     * 返回所有的省信息
     * @return List
     */
    @RequestMapping("index")
    public List<MapProvince> index(){
        List<MapProvince> provinceList = null;
        try {
            // 查询省份信息
            provinceList = provinceFacade.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return provinceList;
    }

    /**
     * 智能回收机新增或更新接口
     * @param wechatOfficialAccountsId 公众号id
     * @param id 联运回收机id
     * @param mchId 机器码
     * @param houseId 所在回收亭id
     * @param provinceId 所在省份id
     * @param cityId 所在城市id
     * @param countyId 所在县id
     * @param address 详细地址
     * @param potX 设备安置点经度
     * @param potY 设备安置点纬度
     * @param description 描述信息
     * @return Map
     */
    @ResponseBody
    @RequestMapping(value = "/saveorupdate", method = RequestMethod.POST)
    public Map<String,Object> saveOrUpdate(String wechatOfficialAccountsId,Integer id,String mchId,Integer houseId,Integer provinceId,Integer cityId,Integer countyId,
                                           String address,String potX,String potY,String description){
        if(CheckUtil.isEmpty(wechatOfficialAccountsId)){
            return  getFailRtn("微信公众号不能为空");
        }
        HsMachineLianyun hsMachineLianyun = new HsMachineLianyun();
        Map<String,Object> paramMap = hlSubstationFacade.getHsIdAndHsSpellByWechatOfficialAccountsId(wechatOfficialAccountsId);
        if(null == paramMap){
            return  getFailRtn("没有与此微信公众号对应的分站");
        }
        paramMap.put(ConstantsSubstation.SUBSTATION_NAME_SPELL,"_"+paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());
        if(!CheckUtil.isEmpty(id)){
            hsMachineLianyun.setId(id);
        }

        if(!CheckUtil.isEmpty(mchId)){
            hsMachineLianyun.setMchid(mchId);
        }else {
            return  getFailRtn("机器码不能为空");
        }

        if(!CheckUtil.isEmpty(houseId)){
            hsMachineLianyun.setHouseId(houseId);
        }else {
            return  getFailRtn("所在服务亭不能为空");
        }

        if(!CheckUtil.isEmpty(provinceId)){
            hsMachineLianyun.setProvinceId(provinceId);
        }else {
            return  getFailRtn("所在省份不能为空");
        }

        if(!CheckUtil.isEmpty(cityId)){
            hsMachineLianyun.setCityId(cityId);
        }else {
            return  getFailRtn("所在城市不能为空");
        }

        if(!CheckUtil.isEmpty(countyId)){
            hsMachineLianyun.setCountyId(countyId);
        }else {
            return  getFailRtn("所在县不能为空");
        }

        if(!CheckUtil.isEmpty(address)){
            hsMachineLianyun.setAddress(address);
        }else {
            return  getFailRtn("详细地址不能为空");
        }

        if(!CheckUtil.isEmpty(potX)){
            hsMachineLianyun.setPotX(potX);
        }else {
            return  getFailRtn("设备安置点经度不能为空");
        }

        if(!CheckUtil.isEmpty(potY)){
            hsMachineLianyun.setPotY(potY);
        }else {
            return  getFailRtn("设备安置点纬度不能为空");
        }

        if(!CheckUtil.isEmpty(description)){
            hsMachineLianyun.setDescription(description);
        }else {
            return  getFailRtn("描述信息不能为空");
        }

        //因为php管理员用户和好嘞的不一致，暂时用分站id代替
        paramMap.put("cuid", paramMap.get(ConstantsSubstation.SUBSTATION_ID));

        try{
            Map<String,String> resultMap = hsMachineLYFacade.saveOrUpdate(hsMachineLianyun,paramMap);
            if( resultMap != null && resultMap.containsKey(MACHINE_ERROR_CODE)){
               //机器码已经存在，请重新输入
                if( resultMap.containsKey(MACHINE_ERROR_CODE)){
                   return  getFailRtn(resultMap.get(MACHINE_ERROR_CODE));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getFailRtn("保存数据出错,请与管理人员联系!");
        }

        return getSuccessRtn("");
    }

    /**
     * 通过wechatOfficialAccountsId和回收机Id获取回收机信息
     * @param wechatOfficialAccountsId 微信公众号id
     * @param id 回收机id
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
            return  getFailRtn("回收机id不能为空");
        }
        Map<String,Object> paramMap = hlSubstationFacade.getHsIdAndHsSpellByWechatOfficialAccountsId(wechatOfficialAccountsId);
        if(null == paramMap){
            return  getFailRtn("没有与此微信公众号对应的分站");
        }
        paramMap.put(ConstantsSubstation.SUBSTATION_NAME_SPELL,"_"+paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());
        HsMachineLianyun hsMachineLianyun = hsMachineLYFacade.selectByIdAndSubstationNameSpell(id,paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());
        return getSuccessRtn(hsMachineLianyun);
    }

    /**
     * 加载回收机信息数据
     * @param wechatOfficialAccountsId 微信公众号ID
     * @param provinceId 省份id
     * @param cityId 城市id
     * @param countryId 县id
     * @param mchId 回收箱编号
     * @param orderStr 排序方式
     * @param page 页数
     * @param size 每页显示数
     * @return Map    返回类型
     * @author liuyang
     * @date 2018年3月8日19:06:45
     */
    @RequestMapping(value="lianYunMachineList")
    @ResponseBody
    public Map<String,Object> lianYunMachineList(String wechatOfficialAccountsId,String mchId, String orderStr,String provinceId,String cityId,
                                                 String countryId,Integer page,Integer size){
        if(CheckUtil.isEmpty(wechatOfficialAccountsId)){
            return getFailRtn("微信公众号不能为空");
        }

        Map<String,Object> paramMap = hlSubstationFacade.getHsIdAndHsSpellByWechatOfficialAccountsId(wechatOfficialAccountsId.trim());
        if(null == paramMap){
            return getFailRtn("没有与此微信公众号对应的分站");
        }
        paramMap.put(ConstantsSubstation.SUBSTATION_NAME_SPELL,"_"+paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());

        if(!CheckUtil.isEmpty(mchId)){
            paramMap.put("mchId", mchId.trim());
        }

        if( !CheckUtil.isEmpty(orderStr)){
            paramMap.put("orderStr",orderStr);
        }

        if( !CheckUtil.isEmpty(provinceId)){
            paramMap.put("provinceId", provinceId);
        }

        if( !CheckUtil.isEmpty(cityId)){
            paramMap.put("cityId", cityId);
        }

        if( !CheckUtil.isEmpty(countryId)){
            paramMap.put("countryId", countryId);
        }

        paramMap.put("page", page != null ? page : 1);
        paramMap.put("size", size != null ? size: 20);

        try {
            Map<String,Object> rtn = getPageResult(hsMachineLYFacade, paramMap);
            List list = (List)rtn.get("data");
            List dataList = new ArrayList();
            list.stream().filter(a -> a != null).forEach(item->{
                HsMachineLianyunDto dataMap = (HsMachineLianyunDto)item;
                Map<String,Object> data = new HashMap<>(16);
                data.put("id",dataMap.getId());
                data.put("mchid",dataMap.getMchid());
                data.put("area",dataMap.getArea());
                //拼接省市县详细地址
                StringBuilder stringBuilder = new StringBuilder();
                MapProvince mapProvince = dataMap.getProvince();
                if(null != mapProvince){
                    stringBuilder.append(mapProvince.getName());
                }
                MapCity mapCity = dataMap.getCity();
                if(null != mapCity){
                    stringBuilder.append(mapCity.getName());
                }
                MapCountry mapCountry = dataMap.getCountry();
                if(null != mapCountry){
                    stringBuilder.append(mapCountry.getName());
                }
                stringBuilder.append(dataMap.getAddress());
                data.put("address",stringBuilder.toString());
                dataList.add(data);
            });
            rtn.put("data",dataList);
            rtn.put("code", ServiceCodeConstants.SUCCESS_RTN);
            return rtn;
        } catch (Exception e) {
            e.printStackTrace();
            return getFailRtn("获取数据失败");
        }
    }

    /**
     * 批量删除回收机
     * @param wechatOfficialAccountsId 微信公众号ID
     * @param machineIds    设定文件
     * @return Map    返回类型
     * @author liuyang
     * @date 2018年3月9日14:06:00
     */
    @RequestMapping(value="/deleteMachine",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> ajaxBatchDelete(String wechatOfficialAccountsId,Integer[] machineIds){
        List<Integer> list = Arrays.asList(machineIds);
        if(CheckUtil.isEmpty(wechatOfficialAccountsId)){
            return getFailRtn("微信公众号不能为空");
        }
        Map<String,Object> paramMap = hlSubstationFacade.getHsIdAndHsSpellByWechatOfficialAccountsId(wechatOfficialAccountsId.trim());
        if(null == paramMap){
            return getFailRtn("没有与此微信公众号对应的分站");
        }
        paramMap.put(ConstantsSubstation.SUBSTATION_NAME_SPELL,"_"+paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());
        try {
            if (!CheckUtil.isEmpty(list) && list.size() > 0) {
                paramMap.put("list", list);
                return hsMachineLYFacade.batchDelMachines(paramMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return getFailRtn("数据删除失败,请与管理员联系");
        }
        return getFailRtn("数据删除失败,请与管理员联系");
    }
}

