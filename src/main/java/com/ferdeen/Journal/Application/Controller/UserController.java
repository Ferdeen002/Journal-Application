package com.ferdeen.Journal.Application.Controller;

import com.ferdeen.Journal.Application.Entity.User;
import com.ferdeen.Journal.Application.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
       return userService.getall();
    }

    @PostMapping
    public  void createUser(@RequestBody User user){
        userService.save(user);
    }
     @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user , @PathVariable String userName){
         User userInDb = userService.findbyUserName(userName);
         if(userInDb != null){
             userInDb.setUserName(user.getUserName());
             userInDb.setPassword(user.getPassword());
             userService.save(userInDb);
         }
         return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }



}
