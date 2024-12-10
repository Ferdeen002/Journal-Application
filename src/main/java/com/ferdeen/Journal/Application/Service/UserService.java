package com.ferdeen.Journal.Application.Service;

import com.ferdeen.Journal.Application.Entity.User;
import com.ferdeen.Journal.Application.Repo.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordencoder = new BCryptPasswordEncoder();


    public void saveUser(User user){
        userRepository.save(user);
    }

    public void saveNewUser(User user){
       user.setPassword(passwordencoder.encode(user.getPassword()));
       user.setRoles(Arrays.asList("USER"));
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
