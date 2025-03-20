package net.eg.journalApp.repository;


import net.eg.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface JournalEntryRepository extends MongoRepository<JournalEntry , ObjectId> {

}