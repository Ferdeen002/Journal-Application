package com.ferdeen.Journal.Application.Repository;

import com.ferdeen.Journal.Application.Repo.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepoImplTests {

   @Autowired
   private UserRepositoryImpl userRepositoryImpl;

    @Test
    public void test(){
       Assertions.assertNotNull(userRepositoryImpl.getUserSA());
    }

}
