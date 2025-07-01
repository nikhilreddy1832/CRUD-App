package com.example;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
@RestController
@RequestMapping("/users")

public class UserController {
    private List<User> users=new ArrayList<>();
    private AtomicLong idcounter = new AtomicLong(1);

    //Post /User ->ADD a new user

    @PostMapping
    public User addUser(@RequestBody User user){
        user.setId(idcounter.getAndIncrement());
        users.add(user);
        return user;
    }
    //Get / users -> Get all users
    @GetMapping
    public List<User> getAllUsers(){
        return users;
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        throw new RuntimeException("User not found with id: "+id); // If user not found
    }
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                user.setName(updatedUser.getName());
                user.setEmail(updatedUser.getEmail());
                return user;
            }
        }
        throw new RuntimeException("User not found with id: " + id);
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                users.remove(user);
                return "User with id " + id + " deleted successfully.";
            }
        }
        throw new RuntimeException("User not found with id: " + id); // If user not found
    }
    @DeleteMapping
    public String deleteAllUsers() {
        users.clear();
        return "All users have been deleted.";
    }
}
