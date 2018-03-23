package com.sound.haolei.consumer.controller;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sound.haolei.facade.HlPointsChannelTypeFacade;


/**
 *@Description  回收价格
 *@author sushile
 *@date 20180312
 */
@RestController
@RequestMapping(value="/recyPrice")
public class RecyclePriceController {

    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}")
    private HlPointsChannelTypeFacade hlPointsChannelTypeFacade;

    /**
     * @Description　php后台查询回收价格
     * @author sushile
     * @date 20180312
     * @return
     */
    @RequestMapping( value = "/search")
    public String recyPriceSearch( String wechatOfficialAccountsId,int currentPage,int pageSize){
        return  hlPointsChannelTypeFacade.findByPage(wechatOfficialAccountsId,currentPage,pageSize);
    }

    /**
     * @Description  php后台编辑收回收价格
     * @author sushile
     * @date 20180312
     * @return
     */
    @RequestMapping( value = "/edit")
    public String recyPriceEdit( String id,String price,String wechatOfficialAccountsId){
           return hlPointsChannelTypeFacade.recyPriceEdit(id,price,wechatOfficialAccountsId);
    }

}
