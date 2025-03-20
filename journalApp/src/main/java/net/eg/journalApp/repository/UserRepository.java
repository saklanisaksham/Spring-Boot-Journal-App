package net.eg.journalApp.repository;

import net.eg.journalApp.entity.User;
import net.eg.journalApp.entity.JournalEntry;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);

    void deleteByUserName(String username);
}