package com.xproject.manager.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


/**
 * Created by whq on 2016/3/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:Test-Spring-Config.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TipsControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    public void setup() {
//        this.mockMvc = webAppContextSetup()
    }
}
