package com.recruit.taskapp.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "records")
public class Record {

    @Id
    @Column(name = "PRIMARY_KEY")
    private String primary_key;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "updated_timestamp", columnDefinition = "TIMESTAMP")
    private LocalDateTime localDateTime;

    public Record(){}

    public Record(String primary_key, String name, String description, LocalDateTime localDateTime){
        this.setPrimaryKey(primary_key);
        this.setName(name);
        this.setDescription(description);
        this.setLocalDateTime(localDateTime);
    }

    private void setPrimaryKey(String primary_key) {
        this.primary_key = primary_key;
    }

    public String getPrimaryKey() {
        return primary_key;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    private void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
