package com.recruit.taskapp.integration.services;

import com.recruit.taskapp.models.Record;
import com.recruit.taskapp.repositories.RecordRepository;
import com.recruit.taskapp.services.RecordService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecordServiceIntegrationTest {

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordRepository recordRepository;

    @Before
    public void setData(){
        recordRepository.save(new Record("PK_test", "Test Name", "Test Desc", LocalDateTime.now()));
    }

    @Test
    public void testGetRecordByPrimaryKey() {
        Record result = recordService.getRecordByPrimaryKey("PK_test");
        assertThat(result).isNotNull();
    }
}
