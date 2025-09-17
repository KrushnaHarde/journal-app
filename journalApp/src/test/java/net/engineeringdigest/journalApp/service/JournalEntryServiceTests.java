package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class JournalEntryServiceTests {

    @Mock
    private JournalEntryRepository journalEntryRepository;

    @InjectMocks
    private JournalEntryService journalEntryService;

    private JournalEntry testEntry;
    private User testUser;
    private ObjectId testId;

    @BeforeEach
    public void setup() {
        testId = new ObjectId();
        testUser = new User();
        testUser.setId(new ObjectId());
        testUser.setUserName("testUser");

        testEntry = new JournalEntry();
        testEntry.setId(testId);
        testEntry.setTitle("Test Title");
        testEntry.setContent("Test Content");
//        testEntry.setTimestamp(LocalDateTime.now());
//        testEntry.setUser(testUser);
    }

    @Test
    public void testSaveEntry() {
        when(journalEntryRepository.save(any(JournalEntry.class))).thenReturn(testEntry);
        journalEntryService.saveEntry(testEntry);
        verify(journalEntryRepository, times(1)).save(testEntry);
    }

    @Test
    public void testGetAll() {
        List<JournalEntry> expectedEntries = Arrays.asList(testEntry);
        when(journalEntryRepository.findAll()).thenReturn(expectedEntries);
        List<JournalEntry> actualEntries = journalEntryService.getAll();
        assertEquals(expectedEntries, actualEntries);
        verify(journalEntryRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        when(journalEntryRepository.findById(testId)).thenReturn(Optional.of(testEntry));
        Optional<JournalEntry> result = journalEntryService.findById(testId);
        assertTrue(result.isPresent());
        assertEquals(testEntry, result.get());
        verify(journalEntryRepository, times(1)).findById(testId);
    }

    @Test
    public void testFindByIdNotFound() {
        ObjectId nonExistentId = new ObjectId();
        when(journalEntryRepository.findById(nonExistentId)).thenReturn(Optional.empty());
        Optional<JournalEntry> result = journalEntryService.findById(nonExistentId);
        assertFalse(result.isPresent());
        verify(journalEntryRepository, times(1)).findById(nonExistentId);
    }

//    @Test
//    public void testDeleteById() {
//        doNothing().when(journalEntryRepository).deleteById(testId);
//        journalEntryService.deleteById(testId);
//        verify(journalEntryRepository, times(1)).deleteById(testId);
//    }

//    @Test
//    public void testFindByUser() {
//        List<JournalEntry> expectedEntries = Arrays.asList(testEntry);
//        when(journalEntryRepository.findByUser(testUser)).thenReturn(expectedEntries);
//        List<JournalEntry> actualEntries = journalEntryService.findByUser(testUser);
//        assertEquals(expectedEntries, actualEntries);
//        verify(journalEntryRepository, times(1)).findByUser(testUser);
//    }
}

