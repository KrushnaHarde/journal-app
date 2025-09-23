package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class UserServiceTests {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private ObjectId testId;

    @BeforeEach
    public void setup() {
        testUser = new User();
        testUser.setUserName("testUser");
        testUser.setPassword("password123");
        testUser.setRole(java.util.Arrays.asList("USER"));
        testUser = userRepository.save(testUser);
        testId = testUser.getId();
    }

    @Test
    public void testSaveEntry() {
        User user = new User();
        user.setUserName("saveEntryUser");
        user.setPassword("pass");
        user.setRole(java.util.Arrays.asList("USER"));
        userService.saveEntry(user);
        User found = userRepository.findByUserName("saveEntryUser");
        assertNotNull(found);
        assertEquals("saveEntryUser", found.getUserName());
    }

    @Test
    public void testSaveNewUser() {
        User user = new User();
        user.setUserName("newUser");
        user.setPassword("plainpass");
        userService.saveNewUser(user);
        User found = userRepository.findByUserName("newUser");
        assertNotNull(found);
        assertTrue(found.getPassword().startsWith("$2a$"));
        assertEquals(java.util.Arrays.asList("USER"), found.getRole());
    }

    @Test
    public void testGetAll() {
        java.util.List<User> users = userService.getAll();
        assertFalse(users.isEmpty());
        assertTrue(users.stream().anyMatch(u -> u.getUserName().equals("testUser")));
    }

    @Test
    public void testFindById() {
        java.util.Optional<User> userOpt = userService.findById(testId);
        assertTrue(userOpt.isPresent());
        assertEquals("testUser", userOpt.get().getUserName());
    }

    @Test
    public void testFindByIdNotFound() {
        ObjectId fakeId = new ObjectId();
        java.util.Optional<User> userOpt = userService.findById(fakeId);
        assertFalse(userOpt.isPresent());
    }

    @Test
    public void testDeleteById() {
        userService.deleteById(testId);
        java.util.Optional<User> userOpt = userRepository.findById(testId);
        assertFalse(userOpt.isPresent());
    }

    @Test
    public void testDeleteByUserName() {
        userService.deleteByUserName("testUser");
        User found = userRepository.findByUserName("testUser");
        assertNull(found);
    }

    @Test
    public void testFindByUserName() {
        User user = userService.findByUserName("testUser");
        assertNotNull(user);
        assertEquals("testUser", user.getUserName());
    }

    @Test
    public void testFindByUserNameNotFound() {
        User user = userService.findByUserName("noSuchUser");
        assertNull(user);
    }

    @Test
    public void testSaveAdmin() {
        User user = new User();
        user.setUserName("adminUser");
        user.setPassword("adminpass");
        userService.saveAdmin(user);
        User found = userRepository.findByUserName("adminUser");
        assertNotNull(found);
        assertTrue(found.getPassword().startsWith("$2a$"));
        assertEquals(java.util.Arrays.asList("USER", "ADMIN"), found.getRole());
    }
}