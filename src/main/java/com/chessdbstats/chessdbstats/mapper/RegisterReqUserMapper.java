package com.chessdbstats.chessdbstats.mapper;

import com.chessdbstats.chessdbstats.model.User;
import com.chessdbstats.chessdbstats.model.request.RegisterReq;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class RegisterReqUserMapper implements Function<RegisterReq, User> {


    @Override
    public User apply(RegisterReq registerReq) {
        return new User(
                registerReq.getEmail(),
                registerReq.getPassword()
        );
    }
}
