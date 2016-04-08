package com.wetrack.ikongtiao.dao.test;

import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.domain.ImSession;
import com.wetrack.ikongtiao.repo.api.FaultTypeRepo;
import com.wetrack.ikongtiao.repo.api.im.ImSessionRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by zhanghong on 15/12/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring/*.xml"}
)
public class FaultTypeTest {

    @Autowired
    FaultTypeRepo faultTypeRepo;

    @Autowired
    ImSessionRepo imSessionRepo;

    @Before
    public void init(){
    }

    @After
    public void clear(){

    }

    @Test
    public void add(){
        ImSession imSession = new ImSession();
        imSession.setStatus(0);
        imSession.setCloudId("test_from");
        imSession.setToCloudId("test_to");
        imSessionRepo.save(imSession);

        ImMessage imMessage = new ImMessage();
        imMessage.setMessageFrom("test_from");
        imMessage.setMessageTo("test_to");
        List<ImSession> imSessionList = imSessionRepo.listImSessionByMessage(imMessage);
        System.out.println(Jackson.mobile().writeValueAsString(imSessionList));

    }

    @Test
    public void list(){
    }



}
