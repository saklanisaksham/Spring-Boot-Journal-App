package net.eg.journalApp.controller;

import net.eg.journalApp.entity.JournalEntry;
import net.eg.journalApp.entity.User;
import net.eg.journalApp.service.JournalEntryService;
import net.eg.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        if (user == null || user.getJournalEntries() == null || user.getJournalEntries().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user.getJournalEntries(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myId) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userService.findByUserName(userName);

            if (user == null || user.getJournalEntries() == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<JournalEntry> collect = user.getJournalEntries()
                    .stream()
                    .filter(x -> x.getId().equals(myId))
                    .collect(Collectors.toList());

            if (!collect.isEmpty()) {
                Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
                if (journalEntry.isPresent()) {
                    return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deleteById(myId, username);

        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            User user = userService.findByUserName(userName);

            if (user == null || user.getJournalEntries() == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            List<JournalEntry> collect = user.getJournalEntries()
                    .stream()
                    .filter(x -> x.getId().equals(myId))
                    .collect(Collectors.toList());

            if (!collect.isEmpty()) {
                Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
                if (journalEntry.isPresent()) {
                    JournalEntry old = journalEntry.get();
                    old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
                    old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : old.getContent());
                    journalEntryService.saveEntry(old);
                    return new ResponseEntity<>(old, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
