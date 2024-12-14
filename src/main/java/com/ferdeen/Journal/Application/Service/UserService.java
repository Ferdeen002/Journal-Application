package com.ferdeen.Journal.Application.Service;

import com.ferdeen.Journal.Application.Entity.User;
import com.ferdeen.Journal.Application.Repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;



    private static final PasswordEncoder passwordencoder = new BCryptPasswordEncoder();


    public void saveUser(User user){
        userRepository.save(user);
    }

    public boolean saveNewUser(User user){
        try{
            user.setPassword(passwordencoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;
        }catch (Exception e){
            log.error("error loging {}" , user.getUserName());
            return false;
        }

    }

    public void saveAdmin(User user){
        user.setPassword(passwordencoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER" , "ADMIN"));
        userRepository.save(user);
    }


    public List<User> getall(){
        return userRepository.findAll();
    }

    public User findbyUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public void deletebyId(ObjectId myid) {
        userRepository.deleteById(myid);
    }

}
