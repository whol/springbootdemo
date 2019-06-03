package com.wang.springbootdemo.controller;

import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/wx/message/{appid}")
public class WxMessageController {

    private final WxMpService wxService;

    /** * 发送模板消息控制器 * @return */
    @PostMapping(value = "sendTemplateMsg")
    public String sendTemplateMsg(@RequestBody WxMpTemplateMessage wxMpTemplateMessage) {
        //实例化模板对象
        /*WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        //设置模板ID
        wxMpTemplateMessage.setTemplateId("E_HgyZsIM3lMazL7dcDohWDu8ulYCxZxx1MEcQK8A9s");
        //设置发送给哪个用户
        wxMpTemplateMessage.setToUser("oSJKe1Qq-o44KYYm8B_1Sp8OfGio");
        //构建消息格式
        List<WxMpTemplateData> list= Arrays.asList(
                new WxMpTemplateData("param1","aaa"),
                new WxMpTemplateData("param2","123"),
                new WxMpTemplateData("param3","王辉")
        );
        wxMpTemplateMessage.setUrl("www.zizhixiaoshe.com");
        //放进模板对象。准备发送
        wxMpTemplateMessage.setData(list);*/
        try {
            //发送模板
            wxService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return "发送成功";
    }
}
