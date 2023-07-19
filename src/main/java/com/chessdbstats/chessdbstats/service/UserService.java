package com.chessdbstats.chessdbstats.service;

import com.chessdbstats.chessdbstats.controller.EditUserForm;
import com.chessdbstats.chessdbstats.mapper.EditUserFormUserMapper;
import com.chessdbstats.chessdbstats.model.User;
import com.chessdbstats.chessdbstats.repository.UserRepositoryImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepositoryImpl userRepositoryImpl;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public void addUser(User user) {
        userRepositoryImpl.saveUser(user);
    }

    public void updateUser(EditUserForm editUserForm, Long id) throws Exception {
        Optional<User> userOpt = userRepositoryImpl.findUserById(id);
        User loggedInUser = customUserDetailsService.getLoggedInUser();
        if(!loggedInUser.getId().equals(id)) {
            throw new RuntimeException();
        }

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (editUserForm.getEmail() != null) {
                User sameEmail = userRepositoryImpl.findUserByEmail(editUserForm.getEmail());
                if (sameEmail != null && !sameEmail.getId().equals(id)) throw new Exception("email already exists");
                user.setEmail(editUserForm.getEmail());
            }
            if (editUserForm.getPassword() != null) user.setPassword(editUserForm.getPassword());
            if (editUserForm.getFirstName() != null) user.setFirstName(editUserForm.getFirstName());
            if (editUserForm.getLastName() != null) user.setLastName(editUserForm.getLastName());
            userRepositoryImpl.saveUser(user);
        }


    }
}
