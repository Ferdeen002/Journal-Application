package com.ferdeen.Journal.Application.Repo;

import com.ferdeen.Journal.Application.Entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface JournalEntryRepo extends MongoRepository<JournalEntry, ObjectId> {
    @Override
    Optional<JournalEntry> findById(ObjectId objectId);
}
