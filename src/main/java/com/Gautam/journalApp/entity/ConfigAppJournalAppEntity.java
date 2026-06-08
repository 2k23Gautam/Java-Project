package com.Gautam.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "config_journal")
@Data
@NoArgsConstructor
public class ConfigAppJournalAppEntity {
    private String key;
    private String value;
}
