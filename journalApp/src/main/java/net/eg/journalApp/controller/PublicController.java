package net.eg.journalApp.controller;

import net.eg.journalApp.entity.User;
import net.eg.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return new ResponseEntity<>("Service is up and running", HttpStatus.OK);
    }


    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        if (user == null) {
            return new ResponseEntity<>("User object is null!", HttpStatus.BAD_REQUEST);
        }

        System.out.println("Received User: " + user); // Debug print


        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUserName(user.getUserName());
        newUser.setPassword(user.getPassword());
        newUser.setSentimentAnalysis(user.isSentimentAnalysis());
        userService.saveNewUser(newUser);

        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }


}
