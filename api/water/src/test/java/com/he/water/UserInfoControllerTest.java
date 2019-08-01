//
//package com.he.water;
//
//import com.he.water.entity.Orders;
//import com.he.water.service.OrdersService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.Date;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class UserInfoControllerTest {
//    @Autowired
//    private WebApplicationContext webContext;   //注入WebApplicationContext
//    private MockMvc mockMvc;
//
//
//    @Before
//    public void setupMockMvc() {
//        mockMvc = MockMvcBuilders
//                .webAppContextSetup(webContext) //设置MockMvc
//                .build();
//    }
//
//
//
////getUserInfo
//    @Test
//    public void getInfo() throws Exception {
//        String ru = mockMvc.perform(get("/sellWater/save")).andReturn().getResponse().getContentAsString();
//        System.out.println(ru);
//    }
//
//    @Test
//    public void getUserList() throws Exception {
//        String ru = mockMvc.perform(get("/sellWater/getUserList")).andReturn().getResponse().getContentAsString();
//        System.out.println(ru);
//    }
//
//    @Test
//    public void getCode() throws Exception {
//        String ru = mockMvc.perform(get("/sellWater/getCode")).andReturn().getResponse().getContentAsString();
//        System.out.println(ru);
//    }
//    @Test
//    public void getAppId() throws Exception {
//        String ru = mockMvc.perform(get("/sellWater/getAppId")).andReturn().getResponse().getContentAsString();
//        System.out.println(ru);
//    }
//    @Test
//    public void unifiedOrder() throws Exception {
//        String ru = mockMvc.perform(post("/sellWater/unifiedOrder")
//                        .param("type","大")
//                        .param("body","SSS")
//                        .param("detail","detail")
//                        .param("total_fee","1")
//                        .param("spbill_create_ip","127.0.0.1")
//                ).andReturn().getResponse().getContentAsString();
//        System.out.println(ru);
//    }
//    @Test
//    public void test() throws Exception {
//        String ru = mockMvc.perform(get("/sellWater/test")
//                .param("mac","11")
//                .param("temp","27")
//                .param("amount","500")
//                 ).andReturn().getResponse().getContentAsString();
//        System.out.println(ru);
//    }
//    @Test
//    public void orders() throws Exception {
//        String ru = mockMvc.perform(get("/orders/pay")
//                .param("orderId","1")
//                ).andReturn().getResponse().getContentAsString();
//        System.out.println(ru);
//    }
//    @Test
//    public void confirm_delivery() throws Exception {
//        String ru = mockMvc.perform(get("/orders/confirm_delivery")
//                .param("orderId","1")
//        ).andReturn().getResponse().getContentAsString();
//        System.out.println(ru);
//    }
//}
