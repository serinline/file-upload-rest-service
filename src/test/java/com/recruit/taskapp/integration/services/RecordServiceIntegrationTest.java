package com.recruit.taskapp.integration.services;

import com.recruit.taskapp.models.Record;
import com.recruit.taskapp.repositories.RecordRepository;
import com.recruit.taskapp.services.RecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecordServiceIntegrationTest {

    @Autowired
    private RecordService recordService;

    @Autowired
    private RecordRepository recordRepository;

    @Test
    public void testGetRecordByPrimaryKey() {
        Record result = recordService.getRecordByPrimaryKey("Key5");
        assertThat(result).isNotNull();
    }

    @Test
    public void testDeleteRecordByPrimaryKey(){
        recordRepository.save(new Record("PK", "Name", "Des",
                LocalDateTime.of(2013, Month.JULY, 29, 19, 30, 40)));
        assertThat(recordService.getRecordByPrimaryKey("PK")).isNotNull();
        recordRepository.deleteById("PK");
        assertThat(recordService.getRecordByPrimaryKey("PK")).isNull();
    }


}
