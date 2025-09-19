package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    public UserService userService;

    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody User user){
        boolean saved = userService.saveNewUser(user);
        if(saved)
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        return new ResponseEntity<>("User with same name already exist",HttpStatus.CONFLICT);
    }

    @GetMapping("/health")
    public String health() {
        return "Public endpoint working";
    }
}
