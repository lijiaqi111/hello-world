package com.sound.haolei.provider.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.sound.haolei.constants.ConstantsSubstation;
import com.sound.haolei.facade.HsMachineLianyunFacade;
import com.sound.haolei.model.HsMachineLianyun;
import com.sound.haolei.model.HsMachineLianyunMchidSubstation;
import com.sound.haolei.provider.dao.HsMachineLianyunMapper;
import com.sound.haolei.provider.dao.HsMachineLianyunMchidSubstationMapper;
import com.sound.haolei.provider.dao.HsMachineLianyunTrackMapper;
import com.sound.haolei.utils.CheckUtil;

/**
 * 联运回收机接口实现
 * @author liuyang
 */
@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class HsMachineLianyunFacadeImpl extends
        BaseFacadeImpl<HsMachineLianyun, HsMachineLianyunMapper> implements HsMachineLianyunFacade{

    @Autowired
    private HsMachineLianyunMapper hsMachineLianyunMapper;
    @Autowired
    private HsMachineLianyunMchidSubstationMapper hsMachineLianyunMchidSubstationMapper;
    @Autowired
    private HsMachineLianyunTrackMapper hsMachineLianyunTrackMapper;
    @Override
    public boolean getIsValid(String mchid) {
        return false;
    }

    @Override
    public void setMachineInToRedis(HsMachineLianyun machine) {

    }

    @Override
    @Transactional
    public Map<String, Object> batchDelMachines(Map<String, Object> param) throws Exception {
        //先删除本表，然后删除中间表
        int cnt = getMapper().batchDelMachines(param);
        //删除中间表的数据
        hsMachineLianyunMchidSubstationMapper.batchDelete((List<Integer>)param.get("list"),"");
        if( param != null && param.containsKey("list")){
            List<?> list = (List<?>)param.get("list");
            if(list.size() == cnt){
                return getSussRtn(null);
            }else{
                return getFailRtn("删除失败");
            }
        }
        return getFailRtn("删除失败");
    }

    @Override
    @Transactional
    public Map<String, Object> saveBeat(String mchid, String nickname, String province, String city, String county, String potX, String potY, String description, String area, String address) {
        return null;
    }

    @Override
    @Transactional
    public Map<String, Object> saveUserTrack(String cardid, String mchid, Integer type, Integer weight) {
        return null;
    }

    @Override
    @Transactional
    public Map<String, Object> saveUser(String cardid) {
        return null;
    }

    @Override
    public void sendNewMachineToCloud(String string, Map<String, String> header) {

    }

    @Override
    public void sendUpdateMachineToCloud(String url, Map<String, String> header) {

    }

    @Override
    public List<HsMachineLianyun> selectTodayUpdateData() {
        return null;
    }

    @Override
    public int selectByMchidOrNickName(Map<String, Object> nickmap) {
        return 0;
    }

    @Override
    @Transactional
    public Map<String, String> saveOrUpdate(HsMachineLianyun lym, Map<String, Object> paramMap) throws Exception {
        String jqmerror = "";
        Map<String,String> resultMap = new HashMap<String,String>(16);

        //根据机器id校验
        Map<String,Object> machidmap = new HashMap<>(16);
        machidmap.put("mchid", lym.getMchid());
        machidmap.put(ConstantsSubstation.SUBSTATION_NAME_SPELL,paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL));

        if (null != lym.getId() && -1 != lym.getId()) {
            lym.setLtime(new Date());
            machidmap.put("id", lym.getId());

            int size = hsMachineLianyunMapper.selectByMchidOrNickName(machidmap);
            if(size>0){
                jqmerror="机器码已经存在，请重新输入";
                resultMap.put("jqmerror", jqmerror);
                return resultMap;
            }
            if( paramMap != null && paramMap.containsKey("cuid")){
                Long cuidL = (Long)paramMap.get("cuid");
                lym.setLuid(cuidL.intValue());
            }
            //BeanUtils.setProperty(lym, ConstantsSubstation.SUBSTATION_NAME_SPELL, paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL));
            lym.setSubstationNameSpell(paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());
            update(lym);
        } else {
            int size = hsMachineLianyunMapper.selectByMchidOrNickName(machidmap);
            if(size>0){
                jqmerror="机器码已经存在，请重新输入";
                resultMap.put("jqmerror", jqmerror);
                return resultMap;
            }

            if( paramMap != null && paramMap.containsKey("cuid")){
                Long cuidL = (Long)paramMap.get("cuid");
                Integer cuid = cuidL.intValue();
                lym.setCuid(cuid);
                lym.setLuid(cuid);
            }
            lym.setUseNumber(1L);
            lym.setCtime(new Date());
            lym.setLtime(new Date());
            lym.setLastOnlineTime(new Date());
            lym.setLastPosTime(new Date());
            HsMachineLianyunMchidSubstation hmlms = new HsMachineLianyunMchidSubstation();
            hmlms.setMchid(lym.getMchid());
            String hSId = "";
            if( paramMap != null && paramMap.containsKey(ConstantsSubstation.SUBSTATION_ID)){
                hSId = paramMap.get(ConstantsSubstation.SUBSTATION_ID).toString();
                if( !CheckUtil.isEmpty(hSId) ){
                    hmlms.setSubstationId(Integer.parseInt(hSId));
                }
            }
            hsMachineLianyunMchidSubstationMapper.insert(hmlms);
            //lym.setId(hmlms.getId());
            //BeanUtils.setProperty(lym, ConstantsSubstation.SUBSTATION_NAME_SPELL, paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL));
            lym.setSubstationNameSpell(paramMap.get(ConstantsSubstation.SUBSTATION_NAME_SPELL).toString());
            /**
             * @Description 根据智能分类回收箱id，查询己有的轨迹信息
             * @author sushile
             * @date 20170808
             * @param trackMap type  tinyint(4) NOT NULL COMMENT '回收类型 1衣物，2 纸张，3 塑料，4 金属，5 混合 6 纸板 7 玻璃',
             */
            List<Map<String,Object>> trackList = hsMachineLianyunTrackMapper.selectTrackByMchId(lym.getMchid(),String.valueOf(paramMap.get("hSId")));
            if( trackList != null
                    && trackList.size() > 0){
                for(Map<String,Object> trackMap:trackList){
                    if( trackMap != null ){
                        if( trackMap.containsKey("type")){
                            String type = String.valueOf( trackMap.get("type"));
                            if( type.equals("1") ){
                                int weight = Integer.parseInt( String.valueOf(trackMap.get("weight")));
                                lym.setWeight1( weight);
                            }
                            if(  type.equals("2") ){
                                int weight = Integer.parseInt( String.valueOf(trackMap.get("weight")));
                                lym.setWeight2( weight);
                            }
                            if(  type.equals("3") ){
                                int weight = Integer.parseInt( String.valueOf(trackMap.get("weight")));
                                lym.setWeight3( weight);
                            }
                            if(  type.equals("4") ){
                                int weight = Integer.parseInt( String.valueOf(trackMap.get("weight")));
                                lym.setWeight4( weight);
                            }
                            if(  type.equals("5") ){
                                int weight = Integer.parseInt( String.valueOf(trackMap.get("weight")));
                                lym.setWeight5( weight);
                            }
                            if(  type.equals("6") ){
                                int weight = Integer.parseInt( String.valueOf(trackMap.get("weight")));
                                lym.setWeight6( weight);
                            }
                            if(  type.equals("7") ){
                                int weight = Integer.parseInt( String.valueOf(trackMap.get("weight")));
                                lym.setWeight7( weight);
                            }
                        }
                    }
                }
            }
            save(lym);
            /**
             * @Description 更新轨迹中己有数据的分站id
             * @author sushile
             * @date 20170808
             */
            hsMachineLianyunTrackMapper.updateTrackBySubId(String.valueOf(paramMap.get("hSId")), lym.getMchid());
        }
        return resultMap;
    }

    @Override
    public HsMachineLianyun selectByIdAndSubstationNameSpell(Integer id, String substationNameSpell) {

        return hsMachineLianyunMapper.selectByPrimaryKey(id,substationNameSpell);
    }

    @Override
    public HsMachineLianyunMapper getMapper() throws Exception {
        return hsMachineLianyunMapper;
    }

}
