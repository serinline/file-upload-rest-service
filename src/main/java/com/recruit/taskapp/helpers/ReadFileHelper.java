package com.recruit.taskapp.helpers;

import com.recruit.taskapp.models.Record;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


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
//                    if (!GenericValidator.isDate(values[3], "yyyy-MM-dd", true)){
//                        return false;
//                    }
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
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT
                             .withFirstRecordAsHeader()
                             .withIgnoreHeaderCase()
                             .withTrim())) {

            List<Record> records = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                Record record = new Record(
                        csvRecord.get("PRIMARY_KEY"),
                        csvRecord.get("NAME"),
                        csvRecord.get("DESCRIPTION"),
                        LocalDateTime.parse(csvRecord.get("UPDATED_TIMESTAMP"))
                );
                records.add(record);
            }

            return records;
        } catch (IOException e) {
            throw new RuntimeException("fail to parse CSV file");
        }
    }
}
