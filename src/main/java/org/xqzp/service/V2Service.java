package org.xqzp.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    private final
    UserServerService userServerService;

    private final
    UserLinkService userLinkService;

    private final
    UserService userService;

    public V2Service(UserServerService userServerService, UserLinkService userLinkService, UserService userService) {
        this.userServerService = userServerService;
        this.userLinkService = userLinkService;
        this.userService = userService;
    }

    public String createContribute(String uuid) {
        //查询用户信息
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("uuid",uuid);
        User user = userService.getOne(userWrapper);

        Map proxyInfoMap = userServerService.getProxyInfoMap(user,"v2ray");

        StringWriter sw=new StringWriter();

        //如果有节点，则处理节点
        if(proxyInfoMap!=null){
            List<Proxy> proxyList = (List<Proxy>) proxyInfoMap.get("proxyList");

            //获取生成订阅的工厂类，用对应的工厂类处理节点，生成单个节点的订阅
            //将单个节点的订阅写入整体订阅中
            for(Proxy proxy:proxyList){
                V2ProxyFactory v2ProxyFactory = ApplicationContextUtil.getBean(proxy.getType());
                String subscribe = v2ProxyFactory.getSubscribe(proxy);
                sw.write(subscribe);
            }
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
