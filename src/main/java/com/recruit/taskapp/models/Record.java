package com.recruit.taskapp.models;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "records")
public class Record {

    @Id
    @Column(name = "PRIMARY_KEY")
    private String primaryKey;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "updated_timestamp", columnDefinition = "TIMESTAMP")
    private LocalDateTime localDateTime;

    public Record(){}

    public Record(String primaryKey, String name, String description, LocalDateTime localDateTime){
        this.setPrimaryKey(primaryKey);
        this.setName(name);
        this.setDescription(description);
        this.setLocalDateTime(localDateTime);
    }

    private void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getPrimaryKey() {
        return primaryKey;
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
