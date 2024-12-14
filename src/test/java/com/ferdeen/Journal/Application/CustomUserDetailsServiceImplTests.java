package com.ferdeen.Journal.Application;

import com.ferdeen.Journal.Application.Entity.User;
import com.ferdeen.Journal.Application.Repo.UserRepository;
import com.ferdeen.Journal.Application.Service.CustomUserDetailsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class CustomUserDetailsServiceImplTests {

   @InjectMocks
   private CustomUserDetailsServiceImpl customUserDetailsService;

   @Mock
    private UserRepository userRepository;

   @BeforeEach
   void setUp(){
       MockitoAnnotations.initMocks(this);
   }

    @Test
    void loadUserbyUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("ram").password("daiundi").roles(new ArrayList<>()).build());
        UserDetails user = customUserDetailsService.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }
}
