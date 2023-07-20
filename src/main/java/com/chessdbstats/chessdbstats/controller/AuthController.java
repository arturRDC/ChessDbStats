package com.chessdbstats.chessdbstats.controller;

import com.chessdbstats.chessdbstats.auth.JwtUtil;
import com.chessdbstats.chessdbstats.mapper.RegisterReqUserMapper;
import com.chessdbstats.chessdbstats.model.User;
import com.chessdbstats.chessdbstats.model.request.LoginReq;
import com.chessdbstats.chessdbstats.model.request.RegisterReq;
import com.chessdbstats.chessdbstats.model.response.ErrorRes;
import com.chessdbstats.chessdbstats.model.response.LoginRes;
import com.chessdbstats.chessdbstats.repository.UserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    @Autowired
    private UserRepositoryCustom userRepositoryCustom;
    @Autowired
    private RegisterReqUserMapper mapper;

    private JwtUtil jwtUtil;
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody LoginReq loginReq)  {

        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
            String email = authentication.getName();
            User user = userRepositoryCustom.findUserByEmail(email);
            String token = jwtUtil.createToken(user);
            LoginRes loginRes = new LoginRes(email,token);

            return ResponseEntity.ok(loginRes);

        }catch (BadCredentialsException e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST,"Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }catch (Exception e){
            ErrorRes errorResponse = new ErrorRes(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody RegisterReq registerReq)  {
        User user = userRepositoryCustom.saveUser(mapper.apply(registerReq));
        String email = userRepositoryCustom.findUserByEmail(user.getEmail()).getEmail();
        return ResponseEntity.status(HttpStatus.OK).body(email);
    }
}