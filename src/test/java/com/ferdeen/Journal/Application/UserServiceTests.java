package com.ferdeen.Journal.Application;


import com.ferdeen.Journal.Application.Entity.User;
import com.ferdeen.Journal.Application.Repo.UserRepository;
import com.ferdeen.Journal.Application.Service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void testFindbyUserName(User user) {
        Assertions.assertNotNull(userService.saveNewUser(user));
    }
}

