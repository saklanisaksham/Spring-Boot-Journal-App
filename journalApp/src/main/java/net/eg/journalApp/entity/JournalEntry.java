package net.eg.journalApp.entity;

import lombok.*;
//import net.eg.journalApp.enums.Sentiment;
import net.eg.journalApp.enums.Sentiment;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class JournalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;

    public void setId(ObjectId id) {
        this.id = id;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public ObjectId getId() {
        return id;
    }

    public @NonNull String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }
//    private Sentiment sentiment;
}