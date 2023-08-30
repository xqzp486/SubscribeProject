package org.xqzp.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xqzp.entity.User;
import org.xqzp.entity.v2.V2ProxyFactory;
import org.xqzp.entity.yamlvo.Proxy;
import org.xqzp.utils.ApplicationContextUtil;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class V2Service {

    @Autowired
    UserServerService userServerService;

    @Autowired
    UserLinkService userLinkService;

    @Autowired
    UserService userService;

    public String createContribute(String uuid) {
        //查询用户信息
        QueryWrapper<User> userwrapper = new QueryWrapper<>();
        userwrapper.eq("uuid",uuid);
        User user = userService.getOne(userwrapper);

        Map proxyInfoMap = userServerService.getProxyInfoMap(user,"v2ray");
        List<Proxy> proxyList = (List<Proxy>) proxyInfoMap.get("proxyList");

        StringWriter sw=new StringWriter();

        //获取生成订阅的工厂类，用对应的工厂类处理节点，生成单个节点的订阅
        //将单个节点的订阅写入整体订阅中
        for(Proxy proxy:proxyList){
            V2ProxyFactory v2ProxyFactory = ApplicationContextUtil.getBean(proxy.getType());
            String subscribe = v2ProxyFactory.getSubscribe(proxy);
            sw.write(subscribe);
        }

        //加入第三方订阅
        String otherSubscription = userLinkService.addOtherSubscription(user);
        sw.write(otherSubscription);

        //对整体再进行一次base64加密
        byte[] bytes2 = sw.toString().getBytes(StandardCharsets.UTF_8);
        String finalstr = Base64.getEncoder().encodeToString(bytes2);
        return finalstr;
    }
}
