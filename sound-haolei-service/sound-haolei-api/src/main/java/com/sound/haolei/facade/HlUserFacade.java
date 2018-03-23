package com.sound.haolei.facade;

import com.sound.haolei.model.HlUser;

import java.beans.Transient;
import java.util.Map;

public interface HlUserFacade extends BaseFacade<HlUser>{
	
        HlUser selectById( String id);

    /**
     * 登陆注册
     * @param tel
     * @param phpUserId
     * @return
     */
    Map<String,Object> loginByTel(String tel, String openId,Integer subustationId);

    /**
     * 完善用户信息
     * @param mobile
     * @param nickName
     * @param sex
     * @param name
     * @param provinceId
     * @param cityId
     * @param countyId
     * @param houseId
     * @param address
     * @return
     */
    Map<String,Object> updateUserInfo(String mobile, String nickName, Integer sex, String name, Integer provinceId, Integer cityId, Integer countyId, Integer houseId, String address);
}
