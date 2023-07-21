package com.chessdbstats.chessdbstats.service;

import com.chessdbstats.chessdbstats.controller.EditUserForm;
import com.chessdbstats.chessdbstats.model.Collection;
import com.chessdbstats.chessdbstats.model.User;
import com.chessdbstats.chessdbstats.repository.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepositoryCustom userRepositoryCustom;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private CollectionService collectionService;

    public void addUser(User user) {
        userRepositoryCustom.saveUser(user);
    }

    public void updateUser(EditUserForm editUserForm, Long id) throws Exception {
        Optional<User> userOpt = userRepositoryCustom.findUserById(id);
        User loggedInUser = customUserDetailsService.getLoggedInUser();
        if(!loggedInUser.getId().equals(id)) {
            throw new RuntimeException();
        }

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (editUserForm.getEmail() != null) {
                User sameEmail = userRepositoryCustom.findUserByEmail(editUserForm.getEmail());
                if (sameEmail != null && !sameEmail.getId().equals(id)) throw new Exception("email already exists");
                user.setEmail(editUserForm.getEmail());
            }
            if (editUserForm.getPassword() != null) user.setPassword(editUserForm.getPassword());
            if (editUserForm.getFirstName() != null) user.setFirstName(editUserForm.getFirstName());
            if (editUserForm.getLastName() != null) user.setLastName(editUserForm.getLastName());
            userRepositoryCustom.saveUser(user);
        }


    }

    public Boolean validateUserHasCollection(Long id) {
        Collection selectedCol = collectionService.getCollectionById(id);

        User loggedInUser = customUserDetailsService.getLoggedInUser();
        return loggedInUser.getCollections().contains(selectedCol);
    }
}
