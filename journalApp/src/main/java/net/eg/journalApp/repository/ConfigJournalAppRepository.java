package net.eg.journalApp.repository;

import net.eg.journalApp.entity.ConfigJournalAppEntity;
import net.eg.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}