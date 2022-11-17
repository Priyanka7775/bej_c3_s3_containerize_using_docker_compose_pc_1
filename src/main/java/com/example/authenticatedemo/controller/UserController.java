package com.example.authenticatedemo.controller;

import com.example.authenticatedemo.domain.User;
import com.example.authenticatedemo.exception.UserNotFoundException;
import com.example.authenticatedemo.services.SecurityTokenGenerator;
import com.example.authenticatedemo.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/userdata/v1/")
public class UserController {

    private UserServices userServices;
    private SecurityTokenGenerator securityTokenGenerator;

    @Autowired
    public UserController(UserServices userServices,SecurityTokenGenerator securityTokenGenerator ) {
        this.userServices = userServices;
        this.securityTokenGenerator=securityTokenGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user)throws UserNotFoundException{
        Map<String,String> map=null;
        try{
            User user1=userServices.findByUsernameAndPassword(user.getUsername(), user.getPassword());
            if(user1.getUsername().equals(user.getUsername())){
                map=securityTokenGenerator.generateToken(user);
            }
            return new ResponseEntity<>(map,HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new UserNotFoundException();
        }catch (Exception e){
            return new ResponseEntity<>("Try after sometime",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        User createdUser = userServices.addUser(user);
        return new ResponseEntity(createdUser , HttpStatus.CREATED);
    }

    @GetMapping("/userdata/v1/fetchusers")
    public ResponseEntity<?> getAllUsers(){
        List<User> userList=userServices.getAllUsers();
        return new ResponseEntity<>(userList,HttpStatus.OK);
    }

}

