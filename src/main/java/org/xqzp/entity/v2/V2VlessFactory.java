package org.xqzp.entity.v2;

import org.springframework.stereotype.Component;
import org.xqzp.entity.yamlvo.Proxy;

import java.util.Map;
import java.util.Set;

@Component(value = "vless")
public class V2VlessFactory extends V2ProxyFactory {
    public String getSubscribe(Proxy proxy){

        String format;

        if("reality".equals(proxy.getOpts().get("security"))){
            String stringFormat = "vless://%s@%s:%d?encryption=none&flow=xtls-rprx-vision" +
                    "&fp=chrome&type=tcp&%s"+"headerType=none#%s"+"\n";

            Set<Map.Entry<String, String>> entries = proxy.getOpts().entrySet();

            String optsString = entries.stream()
                    .reduce(new StringBuilder(),
                            (sb, entry) -> sb.append(entry.getKey())
                                    .append("=")
                                    .append(entry.getValue())
                                    .append("&"),
                            StringBuilder::append)
                    .toString();

            format = String.format(stringFormat, proxy.getUuid(), proxy.getServer(),
                                                 proxy.getPort(),optsString,proxy.getName());

        }else {
            String stringFormat = "vless://%s@%s:%d?encryption=none&flow=xtls-rprx-vision" +
                    "&security=tls&fp=chrome&type=tcp&headerType=none#%s"+"\n";
            format = String.format(stringFormat, proxy.getUuid(), proxy.getServer(), proxy.getPort(), proxy.getName());
        }
        return format;
    }
}