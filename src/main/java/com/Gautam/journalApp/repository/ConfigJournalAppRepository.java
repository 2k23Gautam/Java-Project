package com.Gautam.journalApp.repository;

import com.Gautam.journalApp.entity.ConfigAppJournalAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigAppJournalAppEntity, ObjectId> {
}
