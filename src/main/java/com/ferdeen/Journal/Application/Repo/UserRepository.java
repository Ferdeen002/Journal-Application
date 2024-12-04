package com.ferdeen.Journal.Application.Repo;

import com.ferdeen.Journal.Application.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String User);
}
