package org.xqzp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.xqzp.entity.User;
import org.xqzp.entity.UserLink;


public interface UserLinkService extends IService<UserLink> {

    public String addOtherSubscription(User user);



}

