package com.sound.haolei.provider.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.Page;
import com.sound.haolei.constants.ConstantsSubstation;
import com.sound.haolei.model.HlPointsChannelType;

import java.util.List;

public interface HlPointsChannelTypeMapper extends IBaseMapper<HlPointsChannelType>{
	/**
	 * 
	* @Title: selectByChannel 
	* @Description: 通过渠道名称查询社区积分类型
	* @param @param channel
	* @param @param spell
	* @param @return    设定文件 
	* @return HlPointsChannelType    返回类型 
	* @throws 
	* @author Lvshiyang
	* @date 2018年3月12日 下午7:16:50
	 */
	HlPointsChannelType selectByChannel(@Param("channel")String channel, 
			@Param(ConstantsSubstation.SUBSTATION_NAME_SPELL)String substationSpell);

    /**
     * @Description 分页查询回收价格
     * @author sushile
     * @date 20180313
     **/
    List<HlPointsChannelType> searchByPage(@Param("substationNameSpell") String substationNameSpell );

    /**
     * @Description 　更新回收价格
     * @author sushile
     * @date 20180313
     */
    void updateSupportPhp( HlPointsChannelType hlPointsChannelType);
    /**
     * 
    * @Title: updateIntelligenceRecyclePrice 
    * @Description: 智能回收价格修改
    * @param @param id
    * @param @param points
    * @param @return    设定文件 
    * @return Integer    返回类型 
    * @throws 
    * @author Lvshiyang
    * @date 2018年3月15日 下午2:24:08
     */
	Integer updateIntelligenceRecyclePrice(@Param("id")Integer id, @Param("points")Integer points, 
			@Param(ConstantsSubstation.SUBSTATION_NAME_SPELL)String spellall);
	/**
	 * 
	* @Title: selectSubAllspellAndId 
	* @Description: 根据公众号获取站点全拼以及其他信息
	* @param @param publicnumberid
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	* @author Lvshiyang
	* @date 2018年3月15日 下午2:37:20
	 */
	Map<String, Object> selectSubAllspellAndId(@Param("publicnumberid")String publicnumberid);
}