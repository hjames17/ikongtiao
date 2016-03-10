package com.wetrack.wechat.ikongtiao;

import com.wetrack.base.container.ContainerContext;
import com.wetrack.ikongtiao.domain.BusinessSettings;
import com.wetrack.ikongtiao.service.api.SettingsService;
import com.wetrack.wechat.service.WeixinMenuGenerator;
import me.chanjar.weixin.common.bean.WxMenu;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by zhanghong on 16/3/9.
 */
@Service
public class IktWeixinMenuGenerator implements WeixinMenuGenerator, InitializingBean{

    static final String TYPE_VIEW = "view";
    static final String GOTO = "GOTO://";

    static final String WEIXIN_OAUTH2_API = "https://open.weixin.qq.com/connect/oauth2/authorize";

    @Autowired
    SettingsService settingsService;
    String redirectUrl;

    String appId;


    @Override
    public WxMenu generateMenu() throws Exception{

        Resource resource = ContainerContext.get().getContext().getResource("classpath:/menu.json");
        InputStream is = resource.getInputStream();
        WxMenu menu = WxMenu.fromJson(is);

        for(WxMenu.WxMenuButton button : menu.getButtons()){
            if(button.getType() != null && button.getType().equals(TYPE_VIEW) && button.getUrl().startsWith(GOTO)){
                button.setUrl(
                        formatUrl(button.getUrl().substring(GOTO.length())));
            }else if(button.getSubButtons() != null) {
                for (WxMenu.WxMenuButton subButton : button.getSubButtons()) {
                    if (subButton.getType().equals(TYPE_VIEW) && subButton.getUrl().startsWith(GOTO)) {
                        subButton.setUrl(
                                formatUrl(subButton.getUrl().substring(GOTO.length())));
                    }
                }
            }
        }
        return menu;
    }

    private String formatUrl(String action) throws Exception{
        String state = String.format("A:%s", action);


        String params = String.format("appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect",
                URLEncoder.encode(appId, "utf-8"), URLEncoder.encode(redirectUrl, "utf-8"), URLEncoder.encode(state, "utf-8"));
        System.out.println(params);

        String url = String.format("%s?%s", WEIXIN_OAUTH2_API, params);

        return url;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BusinessSettings settings = settingsService.getBusinessSettings();
        appId = settings.getWechatAppId();
        redirectUrl = settings.getWechatRedirectHandlerUrl();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String api = "http://weixin.weiwaisong.com/wechat/redirect";
        String appId = "wxfb738dfd1f310531";
        String state = String.format("A:%s", "Mission");


        String params = String.format("appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect",
                URLEncoder.encode(appId, "utf-8"), URLEncoder.encode(api, "utf-8"), URLEncoder.encode(state, "utf-8"));
        System.out.println(params);

        String url = String.format("%s?%s", WEIXIN_OAUTH2_API, params);

        System.out.println(url);
    }

    public SettingsService getSettingsService() {
        return settingsService;
    }
}
