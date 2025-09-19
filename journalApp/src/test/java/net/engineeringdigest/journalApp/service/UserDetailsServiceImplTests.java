package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;


@SpringBootTest
public class UserDetailsServiceImplTests {

    @InjectMocks
    private  UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testLoadUserByUserName(){
        Mockito.when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn((net.engineeringdigest.journalApp.entity.User) User.builder()
                .username("ram")
                .password("abc")
                .roles("USER")
                .build());
        UserDetails user = userDetailsService.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }

}
