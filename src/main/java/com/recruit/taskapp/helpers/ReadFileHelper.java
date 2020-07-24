package com.recruit.taskapp.helpers;

import com.opencsv.bean.CsvToBeanBuilder;
import com.recruit.taskapp.models.Record;
import org.apache.commons.validator.GenericValidator;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;


public class ReadFileHelper {

    public static boolean validateFile(MultipartFile file){
        try(BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            String line;
            int counter = 0;
            while ((line = fileReader.readLine()) != null) {
                counter++;
                if (counter == 1 && !line.equals("PRIMARY_KEY,NAME,DESCRIPTION,UPDATED_TIMESTAMP")){
                    return false;
                }
                if (counter >= 2 && counter <= 5){
                    String[] values = line.split(",");
                    if (values.length != 4){
                        return false;
                    }
                    if (values[0].isEmpty() || !values[0].getClass().equals(String.class)){
                        return false;
                    }
                    if(!values[1].getClass().equals(String.class) || !values[2].getClass().equals(String.class)){
                        return false;
                    }
                    if (!GenericValidator.isDate(values[3], "yyyy-MM-dd", true)){
                        return false;
                    }
                }
                if(counter == 6 && !(line.trim().equals("") || line.trim().equals("\n"))){
                    return false;
                }
                if(counter > 6){
                    return false;
                }
            }
        }  catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }

    public static boolean hasProperFormat(MultipartFile file) {
        return "text/csv".equals(file.getContentType());
    }

    public static List processDataToRecords(InputStream is) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
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
