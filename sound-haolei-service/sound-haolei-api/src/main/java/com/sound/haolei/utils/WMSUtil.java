package com.sound.haolei.utils;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.sound.haolei.exception.HttpClientException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
* @ClassName: WMSUtil 
* @Description: wms系统工具类 
* @author wangzhanguo 
* @date 2017年8月17日 上午11:32:37 
*  
 */
@SuppressWarnings("unused")
public class WMSUtil {
	private static final String PRO_FILE_PATH = "wms.properties";
	private static String baseUrl;//ip和端口号
	private static String salt;//密钥
	
	//商品出入库
	private static final String IOADD = "/api/stock/io-add";
	//商品分类查询
	private static final String CATEGORY = "/api/category/index";
	//商品添加
	private static final String GOODSADD = "/api/sku/add";
	//商品详情
	private static final String GOODSDETAIL = "/api/sku/detail";
	//分类详情
	private static final String CATEGORYDETAIL = "/api/category/get-info";
	//库存盘点
	private static final String GOODSCHECK = "/api/stock/check-add";
	//ssu添加
	private static final String SSUADD = "/api/ssu/add";
	//库存管理列表
	private static final String STOCKMANAGERLIST = "/api/stock/check-index";
	//库存统计列表
	private static final String STOCKCOUNTLIST = "/api/stock/day-count";
	//出入库管理
	private static final String INOROUTSTOCKLIST = "/api/stock/io-index";
	//出入库详情
	private static final String INOROUTSTOCKDETAIL = "";
	//新增库存盘点-抄库(对应实时库存)
	private static final String CHECKSTOCK = "/api/stock/rt-info";
	//新增库存盘点
	private static final String ADDCHECKSTOCK = "/api/stock/check-batch-add";
	//库存盘点详情
	private static final String CHECKSTOCKDETAIL = "/api/stock/check-info";
	
	static{
		//加载配置
		ProfileUtil profileUtil = ProfileUtil.getInstance();
		baseUrl = profileUtil.read(PRO_FILE_PATH, "wms_base_url");
		salt = profileUtil.read(PRO_FILE_PATH, "wms_salt");
	}
	
	/** 
	* @Title: stockManagerList 
	* @Description: TODO(获取库存管理列表) 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	* @author zhaoming
	* @date 2017年12月13日 下午5:28:00
	*/
	public static Map<String,Object> stockManagerList(Map<String,Object> paramMap){
		return sendReqToWMS(baseUrl+STOCKMANAGERLIST,paramMap);
	}
	/** 
	* @Title: checkStockDetail 
	* @Description: TODO(盘点库存详情) 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	* @author zhaoming
	* @date 2017年12月26日 下午4:14:33
	*/
	public static Map<String,Object> checkStockDetail(Map<String,Object> paramMap){
		return sendReqToWMS(baseUrl+CHECKSTOCKDETAIL,paramMap);
	}
	/** 
	* @Title: addCheckStock 
	* @Description: TODO(批量添加库存盘点) 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	* @author zhaoming
	* @date 2017年12月25日 下午3:42:21
	*/
	public static Map<String,Object> addCheckStock(Map<String,Object> paramMap){
		return sendReqToWMS(baseUrl+ADDCHECKSTOCK,paramMap);
	}
	/** 
	* @Title: stockCountList 
	* @Description: TODO(分页查询库存统计列表) 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	* @author zhaoming
	* @date 2017年12月14日 下午5:47:35
	*/
	public static Map<String,Object> stockCountList(Map<String,Object> paramMap){
		return sendReqToWMS(baseUrl+STOCKCOUNTLIST,paramMap);
	}
	/** 
	* @Title: inStockList 
	* @Description: TODO(出入库管理列表) 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	* @author zhaoming
	* @date 2017年12月14日 下午6:02:04
	*/
	public static Map<String,Object> inOrOutStockList(Map<String,Object> paramMap){
		return sendReqToWMS(baseUrl+INOROUTSTOCKLIST,paramMap);
	}
	/** 
	* @Title: inOrOutStockDetail 
	* @Description: TODO(出入库详情) 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	* @author zhaoming
	* @date 2017年12月14日 下午6:10:15
	*/
	public static Map<String,Object> inOrOutStockDetail(Map<String,Object> paramMap){
		return sendReqToWMS(baseUrl+INOROUTSTOCKDETAIL,paramMap);
	}
	/** 
	* @Title: checkStock 
	* @Description: TODO(新增盘点库存-抄库) 
	* @param @param paramMap
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	* @author zhaoming
	* @date 2017年12月15日 下午3:39:53
	*/
	public static Map<String,Object> checkStock(Map<String,Object> paramMap){
		return sendReqToWMS(baseUrl+CHECKSTOCK,paramMap);
	}
	/**
	 * 
	* @Title: ioAdd 
	* @Description: 出入库
	* @param paramMap
	* @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	* @author wangzhanguo
	* @date 2017年8月17日 下午12:07:15
	 */
	public static Map<String,Object> ioAdd(Map<String,Object> paramMap){
		return sendReqToWMS(baseUrl+IOADD,paramMap);
	}
	
	/**
	 * 
	* @Title: ioAdd 
	* @Description: 分类查询
	* @param paramMap
	* @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	* @author wangzhanguo
	* @date 2017年8月17日 下午12:07:15
	 */
	public static Map<String,Object> queryCategory(Map<String,Object> paramMap){
		return sendReqToWMS(baseUrl+CATEGORY,paramMap);
	}
	
	/**
	 * 
	* @Title: goodsAdd 
	* @Description: 商品添加
	* @param paramMap
	* @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	* @author wangyonghui
	* @date 2017-11-17 15:29:44
	 */
	public static Map<String,Object> goodsAdd(Map<String,Object> paramMap){
		return sendReqToWMS(baseUrl+GOODSADD,paramMap);
	}
	
	/**
	 * 
	* @Title: goodsDetail 
	* @Description: 商品详情
	* @param paramMap
	* @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	* @author wangyonghui
	* @date 2017-11-17 15:29:03
	 */
	public static Map<String,Object> goodsDetail(Map<String,Object> paramMap){
		return sendReqToWMS(baseUrl+GOODSDETAIL,paramMap);
	}
	
	/**
	 * 
	* @Title: categoryDetail 
	* @Description: 分类详情
	* @param paramMap
	* @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	* @author wangyonghui
	* @date 2017-11-17 15:29:03
	 */
	public static Map<String,Object> categoryDetail(Map<String,Object> paramMap){
		return sendReqToWMS(baseUrl+CATEGORYDETAIL,paramMap);
	}
	
	/**
	 * 
	* @Title: goodsCheck 
	* @Description: 库存盘点
	* @param paramMap
	* @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	* @author wangyonghui
	* @date 2017-11-17 15:29:03
	 */
	public static Map<String,Object> goodsCheck(Map<String,Object> paramMap){
		return sendReqToWMS(baseUrl+GOODSCHECK,paramMap);
	}
	
	/**
	 * 
	* @Title: ssuAdd 
	* @Description: ssu添加
	* @param paramMap
	* @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws 
	* @author wangyonghui
	* @date 2017-11-27 15:32:07
	 */
	public static Map<String,Object> ssuAdd(Map<String,Object> paramMap){
		return sendReqToWMS(baseUrl+SSUADD,paramMap);
	}
	
	/**
	 * 
	* @Title: sendReqToWMS 
	* @Description: 请求WMS接口
	* @param uri
	* @param paramMap
	* @return    设定文件 
	* @return Map<String,Object>    返回类型 
	 * @throws HttpClientException 
	 * @throws IOException 
	 * @throws ParseException 
	* @throws 
	* @author wangzhanguo
	* @date 2017年8月17日 下午12:18:57
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> sendReqToWMS(String uri,Map<String,Object> paramMap){
		Map<String,String> strParam = toStrMap(paramMap);
		//请求头
		Map<String, String> headers = new HashMap<>();
		headers.put("Content-type", "application/x-www-form-urlencoded");
		headers.put("Accept", "application/json");
		headers.put("api-token", SignUtil.createWMSToken(strParam,salt));
		String result = null;
		try {
			result = HttpClientUtil.post(uri, headers, strParam);
		} catch (IOException | HttpClientException e) {
			e.printStackTrace();
		}
		Map<String,Object> resMap = null;
		if(result != null && result.length() > 0){
        	try {
				resMap = JSON.parse(result, Map.class);
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
		return resMap;
	}
	
	private static Map<String,String> toStrMap(Map<String,Object> paramMap){
		Map<String,String> strParam  = new HashMap<>();
		for (Map.Entry<String, Object> param : paramMap.entrySet()) {
			String paramVal = "";
        	if(param.getValue() != null){
        		paramVal = String.valueOf(param.getValue());
        	}
        	strParam.put(param.getKey(), paramVal);
        }
		return strParam;
	}
}
