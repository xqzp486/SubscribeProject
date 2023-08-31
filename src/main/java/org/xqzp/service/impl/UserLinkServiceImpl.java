package org.xqzp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xqzp.entity.*;
import org.xqzp.mapper.UserLinkMapper;
import org.xqzp.service.SubscriptionLinkService;
import org.xqzp.service.UserLinkService;

import java.io.StringWriter;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service("userLinkService")
public class UserLinkServiceImpl extends ServiceImpl<UserLinkMapper, UserLink> implements UserLinkService {

    @Autowired
    private SubscriptionLinkService subscriptionLinkService;

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 获取第三方订阅
     * @param user 用户
     * @return 第三方订阅整合成的字符串
     * 1. 根据用户信息查询用户拥有的第三方订阅信息
     * 2. 将第三方订阅整合成一个字符串
     *      2.1 如果是http或者https开头的订阅,则先发送请求去获取，再写入
     *      2.2 如果直接是v2ray分享格式的订阅,则直接写入
     */
    @Override
    public String addOtherSubscription(User user) {
        StringWriter stringWriter = new StringWriter();

        //根据用户信息查询用户拥有的第三方订阅信息
        //1.先获取user和subscription_link的对应关系
        QueryWrapper<UserLink> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",user.getId());
        List<UserLink> userLinkList = baseMapper.selectList(wrapper);

        List<Integer> array = userLinkList.stream()
                .map((UserLink::getLid))
                .collect(Collectors.toList());

         //2.获取到对应的订阅链接
        List<SubscriptionLink> subscriptionLinkList = subscriptionLinkService.listByIds(array);

        subscriptionLinkList.forEach(
                subscriptionLink -> {
                    String link = subscriptionLink.getLink();
                    //如果是http或者https开头的订阅,则先发送请求去获取，再写入
                    if(StringUtils.startsWith(link,"http")||StringUtils.startsWith(link,"https")){
                        String base64 = restTemplate.getForObject(link, String.class);
                        //base64解码之后写入订阅中
                        stringWriter.write(new String(Base64.getDecoder().decode(base64)));
                    }
                    //如果直接是v2ray分享格式的订阅,则直接写入
                    stringWriter.write(subscriptionLink.getLink()+"\n");
                });
        return stringWriter.toString();
    }
}