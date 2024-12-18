package com.ferdeen.Journal.Application.Entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;

@Document(collection = "config_journal")
@Data
public class ConfigJournal {

    private String key;
    private String value;


}
