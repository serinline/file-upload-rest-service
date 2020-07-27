package com.recruit.taskapp.helpers;

import com.recruit.taskapp.models.Record;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@SpringBootTest
public class ReadFileHelperTest {

    @Autowired
    private ReadFileHelper readFileHelper;

    private MultipartFile properFile;
    private MultipartFile notProperFile;

    @Before
    public void setUp()  {
        properFile = new MockMultipartFile(
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

        notProperFile = new MockMultipartFile(
                "file",
                "test_file.txt",
                MediaType.TEXT_PLAIN_VALUE,
                ("PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMP\n").getBytes()
        );
    }

    @Test
    public void testHasProperFormat(){
        Assert.assertTrue(readFileHelper.hasProperFormat(properFile));
    }

    @Test
    public void testValidateFile(){
        Assert.assertTrue(readFileHelper.validateFile(properFile));
        Assert.assertFalse(readFileHelper.validateFile(notProperFile));
    }

    @Test
    public void testProcessDataToRecords() throws IOException {
        List<Record> result = readFileHelper.processDataToRecords(properFile.getInputStream());
        Assert.assertEquals("Test description2", result.get(1).getDescription());
        Assert.assertTrue(result.size() == 4);
    }

}
