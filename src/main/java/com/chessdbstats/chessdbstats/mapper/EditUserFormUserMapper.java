package com.chessdbstats.chessdbstats.mapper;

import com.chessdbstats.chessdbstats.controller.EditUserForm;
import com.chessdbstats.chessdbstats.model.User;
import com.chessdbstats.chessdbstats.model.request.RegisterReq;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EditUserFormUserMapper implements Function<EditUserForm, User> {


    @Override
    public User apply(EditUserForm editUserForm) {
        User user = new User();
        if (editUserForm.getEmail() != null) user.setEmail(editUserForm.getEmail());
        if (editUserForm.getPassword() != null) user.setPassword(editUserForm.getPassword());
        if (editUserForm.getFirstName() != null) user.setFirstName(editUserForm.getFirstName());
        if (editUserForm.getLastName() != null) user.setLastName(editUserForm.getLastName());

        return user;
    }
}
