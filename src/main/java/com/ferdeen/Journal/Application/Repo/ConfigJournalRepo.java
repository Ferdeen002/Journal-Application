package com.ferdeen.Journal.Application.Repo;

import com.ferdeen.Journal.Application.Entity.ConfigJournal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalRepo extends MongoRepository<ConfigJournal , ObjectId> {
}
