package com.Gautam.journalApp.repository;

import com.Gautam.journalApp.entity.User;
import com.Gautam.journalApp.enums.AuthProviderType;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String username);
    void deleteByUserName(String username);
    User findByEmail(String email);
    User findByProviderIdAndAuthProviderType(String providerId, AuthProviderType authProviderType);
}
