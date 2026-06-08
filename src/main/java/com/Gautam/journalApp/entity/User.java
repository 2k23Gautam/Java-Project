package com.Gautam.journalApp.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Getter
@Setter
public class User {

    @Id
    private ObjectId id;

//    searching will be fast because of indexed and unique true will make sure that each username is unique
    //by default indexing is not done by spring boot we have to do it by going into application property
    @Indexed(unique = true)
    @NonNull
    private String userName;
    @NonNull
    private String password;

    private String email;
    private boolean sentimentalAnalysis;

    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<String>roles;

}
