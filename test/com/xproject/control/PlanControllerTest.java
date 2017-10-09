package com.xproject.control;

/**
 * Created by clm on 2016/3/7.
 */

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.junit.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
        "file:web/WEB-INF/springmvc-servlet.xml",
        "Test-Spring-Usecase.xml"
        })
 public class PlanControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testSave() throws Exception {
        mockMvc.perform(post("/v2/user/loginapi.action").param("json", "{\"pid\":1,\"aid\":3,\"username\":\"bjyz@admin\",\"userpassword\":\"c4ca4238a0b923820dcc509a6f75849b\"}")).andExpect(status().isOk());
    }

    @Test
    public void test_sendNetcomplaint() throws Exception {
        Integer[] categoryid = {1,2,3};
        mockMvc.perform(post("/thirdapi/sendNetcomplaint.action").param("json", "[{id:1}]")).andExpect(status().isOk());
    }
}
