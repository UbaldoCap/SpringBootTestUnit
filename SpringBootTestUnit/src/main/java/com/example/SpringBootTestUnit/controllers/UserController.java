package com.example.SpringBootTestUnit.controllers;

import com.example.SpringBootTestUnit.entities.User;
import com.example.SpringBootTestUnit.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepo userRepo;

    @PostMapping("/add")
    public User add(@RequestBody User user) {
        return userRepo.save(user);
    }

    @GetMapping("/getall")
    public List<User> getall() {
        return userRepo.findAll();
    }

    @GetMapping("get/{id}")
    public Optional<User> getById(@PathVariable long id) {
        if (userRepo.findById(id).isPresent()) {
            return userRepo.findById(id);
        } else {
            return Optional.empty();
        }
    }

    @PutMapping("/update/{id}")
    public Optional<User> updateById(@RequestBody User user, @PathVariable long id) {
        Optional<User> userUpdate = userRepo.findById(id);
        if (userUpdate.isPresent()) {
            userUpdate.get().setUsername(user.getUsername());
            userUpdate.get().setPassword(user.getPassword());
            userRepo.save(userUpdate.get());
            return userUpdate;
        } else {
            return Optional.empty();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<User> delete(@PathVariable long id) {
        if (userRepo.findById(id).isPresent()) {
            userRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
