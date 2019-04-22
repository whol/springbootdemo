package com.wang.springbootdemo.common.enums;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 返回信息枚举
 */
public enum ReturnInfoEnums {
    PROCESS_SUCCESS("0000", "操作成功", "操作成功"),
    PROCESS_FAILED("2999", "操作失败", "操作失败"),
    PROCESS_ERROR("9999", "系统异常", "系统异常"),
    PROCESS_NULL("0000", "查询数据为空", "查询数据为空"),

    MSG_WITH_EXT("0000", "带有自定义参数：{}的信息枚举");

    //返回的结果编码
    private String code;
    //返回给前端的消息
    private String message;
    //后台日志消息
    private String logMessage;

    ReturnInfoEnums() {
    }

    private ReturnInfoEnums(String code, String message) {
        this.code = code;
        this.message = message;
    }
    private ReturnInfoEnums(String code, String message, String logMessage) {
        this.code = code;
        this.message = message;
        this.logMessage = logMessage;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public String getMessage(Object object) {
        //return StringUtils.replace(message, "{}", String.valueOf(object));
        return StrUtil.format(message, object);
    }

    public static void main(String[] args) {
        System.out.println(ReturnInfoEnums.PROCESS_NULL);
        System.out.println(ReturnInfoEnums.PROCESS_FAILED);
        System.out.println(ReturnInfoEnums.MSG_WITH_EXT.getMessage("123哈哈哈"));

    }
}
