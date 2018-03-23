package com.sound.haolei.provider.dao;

import com.sound.haolei.model.HlOldRecycle;

import java.util.List;
import java.util.Map;

public interface HlOldRecycleMapper extends IBaseMapper<HlOldRecycle>{
	/**
	 * @describe:根据订单号更新状态或其他字段
	 * @author wangruwei
	 * @date 2018年3月13日下午2:22:02
	 * @param hloldRecycle
	 */
	int updateByCondition(HlOldRecycle hloldRecycle);

	/**
	 * @describe:对php的分页接口
	 * @author wangruwei
	 * @date 2018年3月13日下午3:48:22
	 * @param csmap
	 * @return
	 */
	List<HlOldRecycle> queryOldRecyclePage(Map<String, Object> csmap);

	/**
	 * @describe:对php的分页接口--查总条数
	 * @author wangruwei
	 * @date 2018年3月20日17:28:14
	 * @param csmap
	 * @return
	 */
    Integer queryOldRecyclePageTotalSize(Map<String, Object> csmap);
}