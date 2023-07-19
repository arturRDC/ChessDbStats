package com.chessdbstats.chessdbstats;

import com.chessdbstats.chessdbstats.model.User;
import com.chessdbstats.chessdbstats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChessDbStatsApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ChessDbStatsApplication.class, args);
    }

    @Autowired
    private UserService userService;
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Executing commands on startup...");
        User demo = new User();
        demo.setEmail("demo@email.com");
        demo.setPassword("demo");
        demo.setFirstName("Demo");
        demo.setLastName("Demo last name");
        userService.addUser(demo);

    }

}
