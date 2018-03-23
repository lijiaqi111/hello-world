package com.sound.haolei.facade;

import java.util.Map;

import com.sound.haolei.model.HsMachineLianyunCard;

public interface HsMachineLianyunCardFacade  extends BaseFacade<HsMachineLianyunCard>{



	/**
	 * @param userId
	 * @author zhaoming
	 * @return
	 */
    Map<String,Object> selectCardByUserId(Integer userId);

	/**
	 * @param communitycommunity
	 * @param street
	 * @return
	 * @author zhaoming
	 */
	Map<String,Object> saveUserToLianYun(Integer userId,Integer subId ,String province,
                                         String city,String country,String area);

	/**
	 * @param page
	 * @param size
	 * @param cardId
	 * @param startDate
	 * @param endDate
	 * @author zhaoming
	 * @return
	 */
    Map<String,Object> selectBindLYCardTrack(Integer page, Integer size, String cardId, String startDate, String endDate,String mobile
	) throws Exception;
}
