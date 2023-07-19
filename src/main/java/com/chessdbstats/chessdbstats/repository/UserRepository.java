package com.chessdbstats.chessdbstats.repository;

import com.chessdbstats.chessdbstats.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepository  extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
