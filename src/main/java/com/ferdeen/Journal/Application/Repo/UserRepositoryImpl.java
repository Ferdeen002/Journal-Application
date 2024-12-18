package com.ferdeen.Journal.Application.Repo;

import com.ferdeen.Journal.Application.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;




public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserSA(){
        Query query = new org.springframework.data.mongodb.core.query.Query();
        Criteria criteria = new Criteria();

//        query.addCriteria(Criteria.where("email").regex())
        query.addCriteria(criteria.orOperator(Criteria.where("email").ne(null).ne(""),
                Criteria.where("sentimentAnalysis").exists(true))
        );
////        query.addCriteria(Criteria.where("userName").is("Vasanth"));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }

}
