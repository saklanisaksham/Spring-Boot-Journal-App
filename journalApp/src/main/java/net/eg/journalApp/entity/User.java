package net.eg.journalApp.entity;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private ObjectId id;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSentimentAnalysis(boolean sentimentAnalysis) {
        this.sentimentAnalysis = sentimentAnalysis;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public void setJournalEntries(List<JournalEntry> journalEntries) {
        this.journalEntries = journalEntries;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Indexed(unique = true)
    @NonNull
    private String userName;

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    private String email;

    public ObjectId getId() {
        return id;
    }

    public @NonNull String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSentimentAnalysis() {
        return sentimentAnalysis;
    }

    public @NonNull String getPassword() {
        return password;
    }

    public List<JournalEntry> getJournalEntries() {
        return journalEntries;
    }

    public List<String> getRoles() {
        return roles;
    }

    private boolean sentimentAnalysis;
    @NonNull
    private String password;
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<String> roles;
}