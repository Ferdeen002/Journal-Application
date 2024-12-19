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
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

// Add criteria to the query
        query.addCriteria(criteria.orOperator(
                Criteria.where("email").regex(emailRegex), // Validate email format using regex
                Criteria.where("sentimentAnalysis").exists(true)
        ));
////        query.addCriteria(Criteria.where("userName").is("Vasanth"));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }

}
