package com.kuang.service;

import java.util.Map;

/**
 * 创作者：陈文震
 * 创作日期：2020年08月26日  21:28
 */
public interface SendSms {

    public boolean send(String phoneNum, String templateCode, Map<String,Object> code);

}
