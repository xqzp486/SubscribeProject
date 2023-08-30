package org.xqzp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xqzp.entity.*;
import org.xqzp.mapper.UserLinkMapper;
import org.xqzp.service.SubscriptionLinkService;
import org.xqzp.service.UserLinkService;

import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

@Service("userLinkService")
public class UserLinkServiceImpl extends ServiceImpl<UserLinkMapper, UserLink> implements UserLinkService {

    @Autowired
    SubscriptionLinkService subscriptionLinkService;


    @Override
    public String addOtherSubscription(User user) {
        StringWriter stringWriter = new StringWriter();

        //======================================================
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

        subscriptionLinkList.forEach(subscriptionLink -> stringWriter.write(subscriptionLink.getLink()));



        return stringWriter.toString();
    }
}