package net.engineeringdigest.journalApp.entity;

// pojo -> plain old java object

//In a typical Spring Boot project, the entity package contains classes that map to database tables.
//These are JPA entities, marked with the @Entity annotation. Each field in the class usually corresponds to a column in the tabl

//entity is a special type of POJO (Plain Old Java Object).
//A POJO is just a simple class with private fields, getters/setters, and no special restrictions.
//But when we annotate a POJO with @Entity, it becomes a JPA-managed entity.
//So, all entities are POJOs, but not all POJOs are entities.

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class JournalEntry {
    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;
}
