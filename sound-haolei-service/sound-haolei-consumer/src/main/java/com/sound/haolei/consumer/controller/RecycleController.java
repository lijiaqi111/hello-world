package com.sound.haolei.consumer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sound.haolei.dto.AppointmentDto;
import com.sound.haolei.dto.HsMachineLianyunTrackDto;
import com.sound.haolei.facade.HsAppointmentFacade;

/**
 * @Descripiton  上门回收
 * @author sushile
 * @date 20180305
 */
@RestController
@RequestMapping(value="/appointment")
public class RecycleController {

    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}",
            timeout=60000)
    private HsAppointmentFacade hsAppointmentFacade;

    /**
     * @Description  保存上门回收
     * @author sushile
     * @date 20180305
     * @return
     */
    @RequestMapping(value = "/save")
    public String saveAppointment( AppointmentDto dto ){
        return  hsAppointmentFacade.saveAppointment( dto );
    }

    /**
     * @Description   根据用户手机号查询投放量量和乐
     * @author sushile
     * @date 20180308
     * **/
    @RequestMapping(value="/monthVal")
    public String mounthVal( String mobile,String wechatOfficialAccountsId ){
       return  hsAppointmentFacade.selectByMobileOpenId( mobile,wechatOfficialAccountsId );
    }

    /**
     * @Description  上门回收记录
     * @author sushile
     * @date 20180309
     * @return
     */
    @RequestMapping(value = "/homeRecy")
    public String  homeRecy(String mobile,String wechatOfficialAccountsId,int currentPage,int pageSize){
            return hsAppointmentFacade.selectHomeRecy(mobile,wechatOfficialAccountsId,currentPage,pageSize);
    }

    /**
     * @Description 智能回收记录
     * @author sushile
     * @date 20180309
     * @return
     */
    @RequestMapping(value = "/machineRecy")
    public String machineRecy(String mobile,String wechatOfficialAccountsId,int currentPage,int pageSize){
       return  hsAppointmentFacade.selectMachineRecy(mobile,wechatOfficialAccountsId,currentPage,pageSize);
    }

    /**
     * @Description 上门回收详情
     * @author sushile
     * @date 20180309
     */
    @RequestMapping(value = "/homeRecyDetai")
    public String homeRecyDetail( String wechatOfficialAccountsId,String id){
         return hsAppointmentFacade.getHomeRecyDetail(wechatOfficialAccountsId,id);
    }

    /**
     * @Description  更新上门回收状态
     * @author sushile
     * @date 20180312
     * @return
     */
    @RequestMapping(value = "/homeRecyUpStatus")
    public String homeRecyUpStatus( String wechatOfficialAccountsId,String id,String status ){
        return hsAppointmentFacade.updateAppointmentStatus(wechatOfficialAccountsId,id,status);
    }

    /**
     * @Description　上门回收列表
     * @author sushile
     * @date 20180320
     */
    @RequestMapping(value = "/homeRecyPhp")
    public String homeRecyPhp( AppointmentDto dto,int currentPage,int pageSize ){
        return this.hsAppointmentFacade.homeRecyPhp(dto,currentPage,pageSize);
    }

    /**
     * @Description 查询回收记录
     * @author lijiaqi
     * @date 20180321
     * @return
     */
    @RequestMapping(value = "/machineRecyPhp")
    public String machineRecyPhp(HsMachineLianyunTrackDto dto,int currentPage,int pageSize){
       return  hsAppointmentFacade.machineRecyPhp(dto,currentPage,pageSize);
    }
    
    
  }
