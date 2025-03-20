package net.eg.journalApp.service;

import net.eg.journalApp.entity.JournalEntry;
import net.eg.journalApp.entity.User;
import net.eg.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
//            logger.info("hahahhahahhahh");
            System.out.println(e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }


    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName){
        boolean removed = false;
        try {
            User user  = userService.findByUserName(userName);
            System.out.println("User: " + user);
            System.out.println("Journal Entries: " + user.getJournalEntries());

            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));

            if(removed){
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while deleting the entry", e);
        }
        return removed;
    }

    public List<JournalEntry> findByUserName(String userName){
        User user = userService.findByUserName(userName);
        return user.getJournalEntries();
    }
}





//  controller --> service --> repository