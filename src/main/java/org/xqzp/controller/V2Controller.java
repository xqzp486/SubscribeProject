package org.xqzp.controller;

import org.springframework.web.bind.annotation.*;
import org.xqzp.exception.ProxyException;
import org.xqzp.service.V2Service;
import org.xqzp.utils.JwtUtils;


@RestController
@RequestMapping("/v2")
public class V2Controller {
    final
    V2Service v2Service;

    public V2Controller(V2Service v2Service) {
        this.v2Service = v2Service;
    }

    @GetMapping("/getContribute")
    public String getContribute(@RequestParam("token") String token){
        String uuid = JwtUtils.getUuidByJwtToken(token);
        String contribute = v2Service.createContribute(uuid);
        if(contribute.isEmpty()){
            throw new ProxyException(404,"没有可以使用的结点");
        }
        return contribute;
    }
}