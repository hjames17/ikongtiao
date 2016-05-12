package com.utils.test;

import com.wetrack.ikongtiao.domain.AppVersion;
import com.wetrack.ikongtiao.service.api.SettingsService;
import com.wetrack.message.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhanghong on 16/1/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring/*.xml"}
)
public class AppVersionTest {

    @Autowired
    MessageService messageService;

    @Autowired
    SettingsService settingsService;
    @Before
    public void init(){
    }

    @Test
    public void testHasNewVersion(){
        try {
            AppVersion version = settingsService.getNewerAppVersion("android", "0.7.0");
            System.out.println(version != null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
