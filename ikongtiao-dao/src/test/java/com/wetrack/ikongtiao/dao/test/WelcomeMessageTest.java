package com.wetrack.ikongtiao.dao.test;

import com.wetrack.ikongtiao.domain.wechat.WechatWelcomeNews;
import com.wetrack.ikongtiao.repo.api.wechat.WechatWelcomeNewsRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhanghong on 15/12/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring/*.xml"}
)
public class WelcomeMessageTest {

//    List<Fixer> list;

    @Autowired
    WechatWelcomeNewsRepo wechatWelcomeNewsRepo;

    @Before
    public void init(){
//        list = new ArrayList<>();
    }


    @Test
    public void list(){
        WechatWelcomeNews news = new WechatWelcomeNews();
        news.setOrdinal(1);
        news.setPicUrl("http://static.waids.cn/images/ikongtiao/welcome_1.png");
        news.setTitle("欢迎关注维大师");
        news.setUrl("http://p.waids.cn/p/weixin/www/news/about.html");
        try {
            wechatWelcomeNewsRepo.create(news);
        } catch (Exception e) {
            e.printStackTrace();
        }

        WechatWelcomeNews news1 = new WechatWelcomeNews();
        news1.setOrdinal(2);
        news1.setPicUrl("http://static.waids.cn/images/ikongtiao/welcome_2.png");
        news1.setTitle("中央空调保养指南");
        news1.setUrl("http://p.waids.cn/p/weixin/www/news/keepFit.html");
        try {
            wechatWelcomeNewsRepo.create(news1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        WechatWelcomeNews news2 = new WechatWelcomeNews();
        news2.setOrdinal(3);
        news2.setPicUrl("http://static.waids.cn/images/ikongtiao/welcome_3.png");
        news2.setTitle("中央空调常见故障排查");
        news2.setUrl("http://p.waids.cn/p/weixin/www/news/solution.html");
        try {
            wechatWelcomeNewsRepo.create(news2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ok");
    }

//    @Test
//    public void testSave(){
//        Fixer fixer = new Fixer();
//        fixer.setName("Mr. Zhang");
//        fixer.setPhone("13588002001");
//        try {
//            fixer = fixerRepo.save(fixer);
//
//        } catch (Exception e) {
//            Assert.fail("save failed " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}
