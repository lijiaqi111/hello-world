package com.sound.haolei.provider.service;

import java.util.List;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Service;
import com.sound.haolei.facade.MapCityFacade;
import com.sound.haolei.model.MapCity;
import com.sound.haolei.model.MapCountry;
import com.sound.haolei.provider.dao.MapCityMapper;

@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class MapCityFacadeImpl extends BaseFacadeImpl<MapCity, MapCityMapper>
        implements MapCityFacade {

    @Override
    public Map<String, Object> selectByLikeName(String name) {
        return null;
    }

    @Override
    public List<MapCountry> selectCountryListByCityName(String city) {
        return null;
    }

    @Override
    public MapCityMapper getMapper() throws Exception {
        return null;
    }

    @Override
    public Long queryCount(Map<String, Object> map) throws Exception {
        return null;
    }

    @Override
    public Map<String, Object> queryPageResult(Map<String, Object> map) throws Exception {
        return null;
    }

    @Override
    public List<MapCity> selectAll() throws Exception {
        return null;
    }

    @Override
    public MapCity selectById(Integer id) throws Exception {
        return null;
    }

    @Override
    public void update(MapCity obj) throws Exception {

    }

    @Override
    public void save(MapCity obj) throws Exception {

    }

    @Override
    public void batchDelete(List<Integer> ids) throws Exception {

    }

    @Override
    public List<MapCity> selectByCondition(Map<String, Object> map) throws Exception {
        return null;
    }

}

