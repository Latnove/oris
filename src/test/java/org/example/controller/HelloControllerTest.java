package org.example.controller;

import org.example.service.impl.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @MockitoBean
    private HelloService helloService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnHelloWithName() throws Exception {
        when(helloService.sayHello("bulka"))
                .thenReturn("Hello, bulka");

        mockMvc.perform(get("/hello")
                        .param("name", "bulka")
                        .with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, bulka"));
    }

    @Test
    void shouldReturnHelloWithoutName() throws Exception {
        when(helloService.sayHello(null))
                .thenReturn("Hello, anonymous");

        mockMvc.perform(get("/hello")
                        .with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, anonymous"));
    }

    @Test
    void shouldCallService() throws Exception {
        when(helloService.sayHello("test"))
                .thenReturn("Hello, test");

        mockMvc.perform(get("/hello")
                        .param("name", "test")
                        .with(user("user").roles("USER")))
                .andExpect(status().isOk());

        org.mockito.Mockito.verify(helloService).sayHello("test");
    }
}