package com.kuang.bilibilikuangshensms;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class BilibiliKuangshenSmsApplicationTests {

    @Test
    void contextLoads() {

        // 连接阿里云
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "KeyId", "密码");
        IAcsClient client = new DefaultAcsClient(profile);

        // 构建请求！
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST); // 不要动
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        // 自定义参数 （手机号，验证码，签名，模板！）
        request.putQueryParameter("PhoneNumbers", "手机号");
        request.putQueryParameter("SignName", "签名名称");
        request.putQueryParameter("TemplateCode", "模版CODE");
        // 构建一个短信的验证码
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 5201314);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));


        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }

}
