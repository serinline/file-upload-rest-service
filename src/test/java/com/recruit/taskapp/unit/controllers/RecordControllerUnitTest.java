package com.recruit.taskapp.unit.controllers;

import com.recruit.taskapp.models.Record;
import com.recruit.taskapp.services.RecordService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecordControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private RecordService recordService;

    private List<Record> records;

    @Before
    public void setUp(){
        Record record1 = new Record("PK1", "Name 1", "Description 1",
                LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40));
        Record record2 = new Record("PK2", "Name 2", "Description 2",
                LocalDateTime.of(2013, Month.JULY, 29, 19, 30, 40));
        records = Arrays.asList(record1, record2);
    }

    @Test
    public void testUploadFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test_file.txt",
                MediaType.TEXT_PLAIN_VALUE,
                ("PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMP\n" +
                        "Key1,Record1,Test description1,2017-03-08T12:30:54\n" +
                        "Key2,Record1,Test description2,2017-03-08T12:30:54\n" +
                        "Key3,Record1,Test description3,2017-03-08T12:30:54\n" +
                        "Key4,Record1,Test description4,2017-03-08T12:30:54\n" +
                        "\n").getBytes()
        );

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(file))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRecordByPrimaryKey() throws Exception {
        when(recordService.getRecordByPrimaryKey("PK1"))
                .thenReturn(records.get(0));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/get/{primaryKey}", "PK1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.primaryKey").value("PK1"));
    }

    @Test
    public void testGetRecordNotExistsByPrimaryKey() throws Exception {
        when(recordService.getRecordByPrimaryKey("PK4"))
                .thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/get/{primaryKey}", "PK4")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteRecordByPrimaryKey() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/delete/{primaryKey}", "PK2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
