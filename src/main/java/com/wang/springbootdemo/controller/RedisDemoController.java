package com.wang.springbootdemo.controller;


import com.wang.springbootdemo.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @class_name: RedisDemoController
 * @package: com.wang.springbootdemo.controller
 * @Description: redis demo 控制层
 * @creat_user: wang
 * @creat_date: 2018/8/26
 * @creat_time: 23:19
 **/
@Slf4j
@RestController
@RequestMapping("redis")
public class RedisDemoController {
    @Autowired
    private RedisUtil redisUtil;

    @RequestMapping(value = "/setRedis", method = RequestMethod.POST)
    public String setRedis(String value, HttpServletRequest httpServletRequest) {
        String result;
        try {
            redisUtil.setValue("redis_key", value);
            httpServletRequest.getSession().setAttribute("my_redis_session", value);
            result = "redis设置成功";
        } catch (Exception e) {
            result = "redis设置失败:" + e.getMessage();
        }
        return result;
    }


    @RequestMapping(value = "/getRedis", method = RequestMethod.GET)
    public String getRedis(HttpServletRequest httpServletRequest) {
        String result;
        try {
            String value = (String) redisUtil.getValue("redis_key");
            result = "redis读取成功:" + value;
            String myRedisSession = (String) httpServletRequest.getSession().getAttribute("my_redis_session");
            System.out.println(myRedisSession);
        } catch (Exception e) {
            result = "redis读取失败:" + e.getMessage();
        }
        return result;
    }
}
