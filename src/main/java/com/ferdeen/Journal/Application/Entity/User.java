package com.ferdeen.Journal.Application.Entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

//import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)

    @NonNull
    private String userName;
    private String email;
    private Boolean sentimentAnalysis;
    @NonNull
    private String password;
    private List<String> roles;

    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();

}
