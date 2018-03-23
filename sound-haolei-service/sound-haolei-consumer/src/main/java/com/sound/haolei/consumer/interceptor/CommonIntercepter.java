package com.sound.haolei.consumer.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.sound.haolei.utils.CheckUtil;
import com.sound.haolei.utils.HttpUtil;
import com.sound.haolei.utils.SignUtil;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CommonIntercepter implements HandlerInterceptor{

    private static String key = "hlpublickey";

    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true
     *    执行下一个拦截器,直到所有的拦截器都执行完毕
     *    再执行被拦截的Controller
     *    然后进入拦截器链,
     *    从最后一个拦截器往回执行所有的postHandle()
     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //获取用户参数
        /*String token = request.getHeader("operatorToken");//用户token，必传
        if(CheckUtil.isEmpty(token)){
            resultMsg(response,-1,"非法请求，无token");
            return false;
        }
        String tokenServer = SignUtil.createWMSToken(HttpUtil.getRequestPararms(request), key);
        //如果秘钥不一样的话，错误
        if(!token.equals(tokenServer)){
            resultMsg(response,-1,"非法请求,token不正确");
            return false;
        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }


    private void resultMsg(HttpServletResponse response,Integer code,String msg) throws IOException{
        resultMsg( response, code, msg,null);
    }

    private void resultMsg(HttpServletResponse response,Integer code,String msg,String data) throws IOException {
        Map<String,Object> back = new HashMap<>();
        back.put("code", code);
        back.put("msg", msg);
        back.put("data", data);
        if(code == 1053 || code == 1054){
            back.put("token", data);
        }
        String josnStr = JSONObject.toJSONString(back);
        backMsg(response,josnStr);
    }

    @SuppressWarnings("unused")
    private void backMsg(HttpServletResponse response,String jsonStr) throws IOException{
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        setHeader(response,jsonStr);
        response.getWriter().write(jsonStr);
    }

    /**
     *
     * @Title: setHeader
     * @Description: 将返回结果放入到header里边去
     * @param response
     * @param jsonStr    设定文件
     * void    返回类型
     * @throws UnsupportedEncodingException
     * @throws
     * @author tianyunyun
     * @date 2017年6月27日 下午3:25:42
     */
    private void setHeader(HttpServletResponse response, String jsonStr){
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        response.setIntHeader("code", jsonObject.getInteger("code"));
        response.setHeader("token", null == jsonObject.getString("data") ? "":jsonObject.getString("data"));
        try {
            response.setHeader("msg", new String(jsonObject.getString("msg") .getBytes("GB2312"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.getMessage();
        }
    }
}
