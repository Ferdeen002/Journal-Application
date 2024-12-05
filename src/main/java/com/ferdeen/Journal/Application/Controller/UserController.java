package com.ferdeen.Journal.Application.Controller;

import com.ferdeen.Journal.Application.Entity.User;
import com.ferdeen.Journal.Application.Repo.UserRepository;
import com.ferdeen.Journal.Application.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers(){
       return userService.getall();
    }


     @PutMapping()
    public ResponseEntity<?> updateUser(@RequestBody User user){
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
         User userInDb = userService.findbyUserName(userName);
             userInDb.setUserName(user.getUserName());
             userInDb.setPassword(user.getPassword());
             userService.save(userInDb);

         return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }

     @DeleteMapping
    public ResponseEntity<?> deletebyUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         userRepository.deleteByUserName(authentication.getName());
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }




}
