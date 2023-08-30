package org.xqzp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xqzp.entity.SubscriptionLink;
import org.xqzp.mapper.SubscriptionLinkMapper;
import org.xqzp.service.SubscriptionLinkService;



@Service("subscriptionLinkService")
public class SubscriptionLinkServiceImpl
        extends ServiceImpl<SubscriptionLinkMapper, SubscriptionLink>
        implements SubscriptionLinkService {


}