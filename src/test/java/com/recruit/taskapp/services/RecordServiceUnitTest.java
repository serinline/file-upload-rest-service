package com.recruit.taskapp.services;

import com.google.gson.Gson;
import com.recruit.taskapp.models.Record;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RecordServiceUnitTest {

    private static RecordService recordService;
    private static List<Record> records;

    @BeforeClass
    public static void setUp() {
        recordService = mock(RecordService.class);

        Record record1 = new Record("PK1", "Name 1", "Description 1",
                LocalDateTime.of(2015, Month.JULY, 29, 19, 30, 40));
        Record record2 = new Record("PK2", "Name 2", "Description 2",
                LocalDateTime.of(2013, Month.JULY, 29, 19, 30, 40));
        records = Arrays.asList(record1, record2);
    }

    @Test
    public void whenGetRecordByPrimaryKey_thenObjectShouldBeReturned(){
        when(recordService.getRecordByPrimaryKey("PK1")).thenReturn(records.get(0));
        String json ="{\"primaryKey\":\"PK1\", " +
                "\"name\":\"Name 1\", " +
                "\"description\":\"Description 1\"," +
                "\"LocalDateTime\":\"2013-07-29T19:30:40\"}";
        Record expected = new Gson().fromJson(json, Record.class);
        Record result = recordService.getRecordByPrimaryKey("PK1");
        assertThat(result.getName())
                .isNotNull()
                .isEqualTo(expected.getName());
    }

    @Test
    public void whenDelete_thenObjectShouldBeDeleted() {
        recordService.deleteRecordByPrimaryKey("PK2");
        verify(recordService, times(1)).deleteRecordByPrimaryKey("PK2");
    }
}
