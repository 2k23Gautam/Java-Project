package com.Gautam.journalApp.repository;

import com.Gautam.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class UserRepositoryImpl {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserforSA(){

        Query query = new Query();

//        query.addCriteria(Criteria.where("userName").is("gautam"));
////        query.addCriteria(Criteria.where("field").lt("10"));

//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne(null).ne(""));

        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"));
        query.addCriteria(Criteria.where("sentimentalAnalysis").is(true));

        //by default and operation is done between this two queries to do or
//        Criteria criteria = new Criteria();
//        query.addCriteria(criteria.orOperator(Criteria.where("email").exists(true),Criteria.where("sentimentalAnalysis").is(true)));
        List<User> users =  mongoTemplate.find(query,User.class);
        return users;
    }
}
