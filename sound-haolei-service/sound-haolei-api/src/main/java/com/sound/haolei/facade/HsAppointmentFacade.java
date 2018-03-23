package com.sound.haolei.facade;

import com.sound.haolei.dto.AppointmentDto;
import com.sound.haolei.dto.HsMachineLianyunTrackDto;

public interface HsAppointmentFacade {


    /**
     * @Description 保存上门回收
     * @author sushile
     * @date 20180308
     * @return
     */
    String saveAppointment( AppointmentDto dto );

    /**
     * @Description 根据手机号和公众号查询
     * @author sushile
     * @date 20180308
     * @return
     */
    String selectByMobileOpenId( String mobile,String openId);

    /**
     * @Description  上门回收记录
     * @author sushile
     * @date 20180309
     * @return
     */
    String  selectHomeRecy( String mobile,String openId,int currentPage,int pageSize);

    /**
     * @Description 智能回收记录
     * @author sushile
     * @date 20180309
     * @return
     */
    String selectMachineRecy(String mobile,String openId,int currentPage,int pageSize);

    /**
     * @Description 上门回收详情
     * @author sushile
     * @date 20180309
     */
    String getHomeRecyDetail(String openId,String id);

    /**
     * @Description 更新上门回收状态
     * @author sushile
     * @date 20180312
     * @return
     */
    String updateAppointmentStatus(String openId,String id,String status);

    /**
     * @Description　上门回收列表
     * @author sushile
     * @date 20180320
     */
    String homeRecyPhp( AppointmentDto dto,int currentPage,int pageSize );
    
    /**
     * @Description　查询回收记录
     * @author lijiaqi
     * @date 20180321
     */
	String machineRecyPhp(HsMachineLianyunTrackDto dto, int currentPage, int pageSize);


}
