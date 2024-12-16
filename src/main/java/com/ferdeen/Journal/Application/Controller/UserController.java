package com.ferdeen.Journal.Application.Controller;

import com.ferdeen.Journal.Application.Entity.User;
import com.ferdeen.Journal.Application.Repo.UserRepository;
import com.ferdeen.Journal.Application.Service.UserService;
import com.ferdeen.Journal.Application.Service.WeatherService;
import com.ferdeen.Journal.Application.api.response.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    WeatherService weatherService;

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
             userService.saveNewUser(userInDb);

         return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }

     @DeleteMapping
    public ResponseEntity<?> deletebyUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         userRepository.deleteByUserName(authentication.getName());
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }

    @GetMapping("greeting")
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
         String greeting = "";
         try{
             if(weatherResponse !=null){
                 greeting = " Weather feels like " + weatherResponse.getCurrent().getFeelslike();
             }
             return new ResponseEntity<>("Hai" + authentication.getName() + greeting ,HttpStatus.OK);
         }catch (Exception e){
             log.error("greeting empty");
             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
    }




}
