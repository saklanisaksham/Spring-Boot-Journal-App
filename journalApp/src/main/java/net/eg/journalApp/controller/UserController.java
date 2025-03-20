package net.eg.journalApp.controller;

import net.eg.journalApp.entity.JournalEntry;
import net.eg.journalApp.entity.User;
import net.eg.journalApp.service.JournalEntryService;
import net.eg.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }


    @GetMapping("id/{id}")
    public User findById(ObjectId id){
        return userService.findById(id).orElse(null);
    }




    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
         
        if (userInDb != null) {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);  // Save the updated user
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if user not found
    }


}