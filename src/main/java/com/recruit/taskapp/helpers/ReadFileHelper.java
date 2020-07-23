package com.recruit.taskapp.helpers;

import com.opencsv.bean.CsvToBeanBuilder;
import com.recruit.taskapp.models.Record;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class ReadFileHelper {

    public static boolean hasProperFormat(MultipartFile file) {
        return "text/csv".equals(file.getContentType());
    }

    public static List processDataToRecords(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
        ) {
            HeaderColumnNameMappingStrategy<Record> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Record.class);

            return new CsvToBeanBuilder(fileReader)
                    .withType(Record.class)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
