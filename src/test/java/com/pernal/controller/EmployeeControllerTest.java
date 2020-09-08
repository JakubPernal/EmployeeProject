package com.pernal.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author pla067jakpern, wrz 07, 2020 CRIF IT Solutions Poland
 **/

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldGet() throws Exception {
        mockMvc.perform(get("/employee/get/100"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"body\":{\"id\":100,\"name\":\"Jakub\",\"surname\":\"Pernal\",\"grade\":10,\"salary\":1000},\"message\":\"OK\",\"status\":\"OK\"}")));
    }

    @Test
    public void shouldPost() throws Exception {
        mockMvc.perform(post("/employee/insert").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" + "    \"name\":\"testn3ame\",\n" + "    \"surname\":\"test su4rname\",\n" + "    \"grade\":15,\n" + "    \"salary\":1060\n" + "}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdate() throws Exception {
        mockMvc.perform(put("/employee/modify/100").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" + "    \"name\":\"Jakub\",\n" + "    \"surname\":\"Pernal\",\n" + "    \"grade\":15,\n" + "    \"salary\":1060\n" + "}"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/employee/get/100"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"body\":{\"id\":100,\"name\":\"Jakub\",\"surname\":\"Pernal\",\"grade\":15,\"salary\":1060},\"message\":\"OK\",\"status\":\"OK\"}")));
    }

    @Test
    public void shouldDelete() throws Exception{
        mockMvc.perform(delete("/employee/delete/200").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(delete("/employee/delete/201").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/employee/get/200"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void shouldFailOnGetNotExisting() throws Exception {
        mockMvc.perform(get("/employee/get/202"))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void shouldFailOnInsertBrokenBody() throws Exception {
        mockMvc.perform(post("/employee/insert").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" + "    \"surname\":\"test su4rname\",\n" + "    \"grade\":15,\n" + "    \"salary\":1060\n" + "}"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldFailOnUpdateBrokenBody() throws Exception {
        mockMvc.perform(put("/employee/modify/300").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" + "    \"surname\":\"test su4rname\",\n" + "    \"grade\":15,\n" + "    \"salary\":1060\n" + "}"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldSearchEmployeesWithEmptyParams() throws Exception {
        mockMvc.perform(post("/employee/search").contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldSearchEmployee() throws Exception {
        mockMvc.perform(post("/employee/search").contentType(MediaType.APPLICATION_JSON)
                .content("{\n" + "    \"name\":\"Jakub\",\n" + "    \"surname\":\"Pernal\",\n" + "    \"grade\":10,\n" + "    \"salary\":1000\n" + "}"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"body\":[{\"id\":100,\"name\":\"Jakub\",\"surname\":\"Pernal\",\"grade\":10,\"salary\":1000}],\"message\":\"OK\",\"status\":\"OK\"}")));
    }
}
