package com.sound.haolei.provider.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.sound.haolei.constants.ConstantsPointsTypeChannel;
import com.sound.haolei.constants.ConstantsUserExtSource;
import com.sound.haolei.constants.HlUserConstants;
import com.sound.haolei.constants.ServiceCodeConstants;
import com.sound.haolei.dto.HlUserExtLoginDto;
import com.sound.haolei.facade.HlPointsChannelTypeFacade;
import com.sound.haolei.facade.HlUserFacade;
import com.sound.haolei.model.HlPointsTrack;
import com.sound.haolei.model.HlUser;
import com.sound.haolei.model.HlUserExt;
import com.sound.haolei.provider.dao.HlPointsTrackMapper;
import com.sound.haolei.provider.dao.HlUserExtMapper;
import com.sound.haolei.provider.dao.HlUserMapper;
import com.sound.haolei.utils.CheckUtil;
import com.sound.haolei.utils.GradeUtil;

/**
 * @author liuyang
 */
@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class HlUserFacadeImpl extends BaseFacadeImpl<HlUser,HlUserMapper>
         implements HlUserFacade {

    @Autowired
    private HlUserMapper hlUserMapper;
    @Autowired
    private HlUserExtMapper hlUserExtMapper;
    @Autowired
    private HlPointsChannelTypeFacade hlPointsChannelTypeFacade;
    @Autowired
    private HlPointsTrackMapper hlPointsTrackMapper;
    @Override
    public HlUser selectById(String id) {
//        return null;
        int idInt = Integer.parseInt( id );
        return hlUserMapper.selectByPrimaryKey( idInt );
    }

    /**
     * 登陆注册
     * @param tel
     * @param openId
     * @param sbustationId
     * @return
     */
    @Override
    public Map<String, Object> loginByTel(String tel, String openId,Integer subustationId) {
        if (CheckUtil.isEmpty(tel) || CheckUtil.isEmpty(openId) || CheckUtil.isEmpty(subustationId)){
            return getFailRtn("必填参数不能为空！");
        }
        if (!CheckUtil.isMobile(tel)) {
            logger.error("手机号格式错误");
            return getFailRtn("您的手机号码貌似不太对哦");
        }
        HlUser user = hlUserMapper.selectByMobile(tel);
        if (null !=user){
            Integer status = user.getStatus() != null?user.getStatus():0;
            // 冻结
            if (status == HlUserConstants.USER_STATUS_FREEZE) {
                logger.debug("账户被冻结");
                return getRtnCode("账户被冻结", ServiceCodeConstants.USER_FREEZED);
            }
            // 更新最近登录时间
            user.setLtime(new Date());
            hlUserMapper.updateByPrimaryKeySelective(user);
            //新增省市区name字段
            HlUserExtLoginDto ext = hlUserExtMapper.selectExtDtoByUserId(user.getId());
            if (null == ext) {
                ext = new HlUserExtLoginDto();
                ext.setUserId(user.getId());
                hlUserExtMapper.insertSelective(ext);
            }
            //机器码存入redis

            // 返回
            Map<String, Object> map = new HashMap<>(16);
            map.put("user", user);
            map.put("userext", ext);

            //查询用户小区名称
            if(user.getHouseId() != null){
                String userHouseName = getUserHouseName(user.getHouseId());
                map.put("userHouseName", userHouseName);
            }
            //绑定微信openid
            hlUserMapper.updateByPrimaryKeySelective(user);
            return getSussRtn(map);
        }
        HlUser hlUser = new HlUser();
        hlUser.setMobile(tel);
        // 密码加密规则
        hlUser.setPasswd("");
        // 注册时间
        Date ctime = new Date();
        hlUser.setCtime(ctime);
        hlUser.setLtime(ctime);
        // 非空字段
        hlUser.setWxOpenid(openId);
        hlUser.setWbOpenid("");
        //用户昵称，使用微信的昵称
        hlUser.setGrade(GradeUtil.getFirstLogin());
        hlUser.setRealname("");
        hlUser.setStatus(0);
        hlUserMapper.insertSelective(hlUser);
        // 用户拓展表
        HlUserExtLoginDto userExt = new HlUserExtLoginDto();
        userExt.setHsBottleAll(0);
        userExt.setHsClothesAll(0);
        userExt.setUserId(hlUser.getId());
        userExt.setHsRole(0);
        userExt.setSource(ConstantsUserExtSource.SOURCE_BY_PHP);
        userExt.setProvinceId(0);
        userExt.setCityId(0);
        userExt.setCountyId(0);
        hlUserExtMapper.insertSelective(userExt);
        // 用户积分轨迹（公众号登陆用户不返回积分）
        Map<String, Object> map = new HashMap<>();

        map.put("user", hlUser);
        map.put("userext", userExt);
        return getSussRtn(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> updateUserInfo(String mobile, String nickName, Integer sex, String name, Integer provinceId, Integer cityId, Integer countyId, Integer houseId, String address){
        HlUser hlUser = hlUserMapper.selectByMobile(mobile);
        HlUserExt hlUserExt = hlUserExtMapper.selectByUserId(hlUser.getId());
        if (!CheckUtil.isEmpty(nickName)){
            hlUser.setNickName(nickName.trim());
        }
        if (!CheckUtil.isEmpty(sex)){
            hlUser.setSex(sex);
        }
        if (!CheckUtil.isEmpty(name)){
            hlUser.setRealname(name);
        }
        if (!CheckUtil.isEmpty(provinceId)){
            hlUserExt.setProvinceId(provinceId);
        }
        if (!CheckUtil.isEmpty(cityId)){
            hlUserExt.setCityId(cityId);
        }
        if (!CheckUtil.isEmpty(countyId)){
            hlUserExt.setCountyId(countyId);
        }
        if (!CheckUtil.isEmpty(houseId)){
            hlUser.setHouseId(houseId);
        }
        if (!CheckUtil.isEmpty(address)){
            hlUserExt.setAddress(address);
        }
        hlUserExt.setSource(ConstantsUserExtSource.SOURCE_BY_PHP);
        int i = hlUserMapper.updateByPrimaryKeySelective(hlUser);
        int j = hlUserExtMapper.updateByPrimaryKey(hlUserExt);
        if(i > 0 || j > 0){
            return getSussRtn("");
        }else {
            return getFailRtn("保存失败，请与管理员联系");
        }
    }

    /**
     * 获取用户小区名称
     * @Title: getUserHouseName
     * @param houseId 小区id
     * @author wangyonghui
     * @date 2017年9月1日 下午3:46:59
     */
    private String getUserHouseName(Integer houseId) {
        String substationNameSpell = hlUserMapper.selectUserHouseSubstationNameSpell(houseId);
        if(CheckUtil.isEmpty(substationNameSpell)){
            substationNameSpell = "";
        }else{
            substationNameSpell = "_" + substationNameSpell;
        }
        return hlUserMapper.selectUserHouseName(substationNameSpell, houseId);
    }
    // 首次登陆用户积分乐豆赠送

    public void updateFirstLoginPointTrack(Integer userId,Integer sbustationId){
        // 用户积分轨迹
        // 等级积分更新操作
        HlPointsTrack track = new HlPointsTrack();
        track.setCtime(new Date());
        track.setDescription("首次登陆送积分");
        track.setPoints(hlPointsChannelTypeFacade.getPointByChannel(ConstantsPointsTypeChannel.POINTS_CHANNEL_FIRST_LOGIN,sbustationId));
        // 等级积分
        track.setType(Byte.valueOf("1"));
        track.setUserId(userId);
        hlPointsTrackMapper.insertSelective(track);
        // 现金积分更新操作
        track = new HlPointsTrack();
        track.setCtime(new Date());
        track.setDescription("首次登陆送乐豆");
        track.setPoints(hlPointsChannelTypeFacade.getPointByChannel(ConstantsPointsTypeChannel.POINTS_CHANNEL_FIRST_LOGIN_CASH,sbustationId));
        // 现金积分
        track.setType(Byte.valueOf("0"));
        track.setUserId(userId);
        hlPointsTrackMapper.insertSelective(track);

    }


    @Override
    public HlUserMapper getMapper() throws Exception {
        return this.hlUserMapper;
    }

}
