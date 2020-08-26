package com.kuang.controller;

import com.aliyuncs.utils.StringUtils;
import com.kuang.service.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 创作者：陈文震
 * 创作日期：2020年08月26日  21:38
 */
@RestController
@CrossOrigin // 跨域支持
public class SmsApiController {
    @Autowired
    private SendSms sendSms;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @GetMapping("/send/{phone}")
    public String code(@PathVariable("phone") String phone) {
        // 调用发送方法 （模拟真实业务 redis）
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return phone + ":" + code + "已存在，还没有过期";
        }

        // 生成验证码并存储带redis中
        code = UUID.randomUUID().toString().substring(0, 4);
        HashMap<String, Object> param = new HashMap<>();
        param.put("code", code);

        boolean isSend = sendSms.send(phone, "模板CODE", param);

        if (isSend) {
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.SECONDS);
            return phone + ":" + code + "发送成功！";
        } else {
            return "发送失败！";
        }
    }
}
