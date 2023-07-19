package com.chessdbstats.chessdbstats.controller;

import com.chessdbstats.chessdbstats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @ResponseBody
    @RequestMapping(value = "/api/v1/users/{userId}",method = RequestMethod.POST)
    public ResponseEntity<String> updateUser(@RequestBody EditUserForm editUserForm, @PathVariable("userId") Long userId) {
        try {
            userService.updateUser(editUserForm, userId);
        } catch (Exception e) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.status(HttpStatus.OK).body("edited profile");
    }
}
