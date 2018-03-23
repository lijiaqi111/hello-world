package com.sound.haolei.consumer.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sound.haolei.facade.HlUserFacade;
import com.sound.haolei.utils.CheckUtil;

/**
 * @author liuyang
 */
@RestController
@RequestMapping(value="/hlUser")
public class HlUserController extends BaseController{
    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}")
    private HlUserFacade hlUserFacade;
    /**
     * 完善用户信息
     * @Title: updateUserInfo
     * @param mobile 用户手机号
     * @param nickName 昵称
     * @param sex 性别 '性别：0 女 1 男',
     * @param name 姓名
     * @param provinceId 省id
     * @param cityId 市id
     * @param countyId 区县id
     * @param houseId 小区id，即服务亭id
     * @param address 详细地址
     * @return
     * @author liuyang
     * @date 2018年3月15日11:08:22
     */
    @RequestMapping(value="/updateUserInfo",method= RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateUserInfo(String mobile, String nickName, Integer sex, String name,
                                                 Integer provinceId, Integer cityId, Integer countyId, Integer houseId, String address){
        if(CheckUtil.isEmpty(mobile)){
            return getFailRtn("手机号不能为空");
        }
        try {
            return hlUserFacade.updateUserInfo(mobile,nickName,sex,name,provinceId,cityId,countyId,houseId, address);
        } catch (Exception e) {
            e.printStackTrace();
            return getFailRtn("保存失败，请与管理员联系");
        }
    }
}
