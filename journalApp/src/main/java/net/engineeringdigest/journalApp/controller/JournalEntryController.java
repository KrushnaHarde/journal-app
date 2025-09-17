package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {
    // special type of @Component which handles HTTP requests

    private Map<ObjectId, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll(){
        return ResponseEntity.ok(new ArrayList<>(journalEntries.values()));
    }

    @PostMapping
    public ResponseEntity<String> createEntry(@RequestBody JournalEntry myEntry){
        // @RequestBody takes the JSON coming in the request and changes it into a Java object we can use in our code
        // in short it takes JSON from post-request and converts it to the JAVA obj

        if(journalEntries.containsKey(myEntry.getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Entry With this ID already Exist");  // 409 - id already exist
        }
        journalEntries.put(myEntry.getId(), myEntry);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Entry Created Successfully");

    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId id) throws Exception{
        JournalEntry entry = journalEntries.get(id);
        if(entry != null)
            return ResponseEntity.ok(entry);    // 200
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Entry with id: " + id + " does not exist");
    }

    @PutMapping("id/{id}")
    public ResponseEntity<String> updateEntry(@PathVariable ObjectId id, @RequestBody JournalEntry myEntry) {
        if (!journalEntries.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404 Not Found
                    .body("Entry with ID " + id + " not found!");
        }

        journalEntries.put(id, myEntry);
        return ResponseEntity.ok("Entry updated successfully");
    }

    @DeleteMapping("id/{id}")
    public ResponseEntity<String> deleteJournalEntryById(@PathVariable ObjectId id) {
        if (!journalEntries.containsKey(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND) // 404 Not Found
                    .body("Entry with ID " + id + " not found!");
        }
        journalEntries.remove(id);
        return ResponseEntity.ok("Entry deleted successfully");
    }
}
