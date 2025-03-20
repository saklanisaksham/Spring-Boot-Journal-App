package net.eg.journalApp.scheduler;

import net.eg.journalApp.entity.JournalEntry;
import net.eg.journalApp.entity.User;
import net.eg.journalApp.repository.UserRepository;
import net.eg.journalApp.repository.UserRepositoryImpl;
import net.eg.journalApp.service.EmailService;
import net.eg.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepository.getUsersforSA();
        for(User user : users){
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))
                    .map(x -> x.getContent()).collect(Collectors.toList());
            String entry = String.join(" ", filteredEntries);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "SEntiment for last 7 days", sentiment);
        }
    }
}
