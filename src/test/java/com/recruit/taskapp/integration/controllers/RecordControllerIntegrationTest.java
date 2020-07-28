package com.recruit.taskapp.integration.controllers;

import com.recruit.taskapp.controllers.RecordController;
import com.recruit.taskapp.models.Record;
import com.recruit.taskapp.repositories.RecordRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class RecordControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    RecordController recordController;

    @Autowired
    RecordRepository recordRepository;

    @Before
    public void setData(){
        recordRepository.save(new Record("PK_test", "Test Name", "Test Desc", LocalDateTime.now()));
    }

    @Test
    public void testGetByPrimaryKey() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/get/PK_test")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Name"));
    }

}
