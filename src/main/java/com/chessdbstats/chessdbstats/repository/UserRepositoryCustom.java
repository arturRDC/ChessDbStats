package com.chessdbstats.chessdbstats.repository;

import com.chessdbstats.chessdbstats.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryCustom {
    @Autowired
    private UserRepository userRepository;
    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }


    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
}