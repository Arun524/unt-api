package com.unt.untstore.controller;

import com.unt.untstore.dto.LoginResponseDto;
import com.unt.untstore.dto.Response;
import com.unt.untstore.dto.UserDto;
import com.unt.untstore.repository.ProductWishlistRepository;
import com.unt.untstore.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private ProductWishlistRepository productWishlistRepository;

    @PostMapping(value = "login", consumes = "application/json")
    public ResponseEntity<LoginResponseDto> login(@RequestBody Map<String, String> request) {

        return new ResponseEntity<LoginResponseDto>(loginService.login(request), HttpStatus.OK);
    }

    @PostMapping(value = "signup", consumes = "application/json")
    public ResponseEntity<Boolean> login(@RequestBody UserDto userDto) {
        boolean response = loginService.signUp(userDto);
        return new ResponseEntity<Boolean>(response, HttpStatus.OK);
    }

    //TODO
    @GetMapping(value = "forgotPassword/{username}")
    public Response<Boolean> forgotPassword(@PathVariable String username) {
        return new Response<>(null);
    }

    //TODO
    @PostMapping(value = "forgotPassword/{username}")
    public Response<Boolean> forgotPassword(@PathVariable String username, @RequestBody String password) {
        return new Response<>(null);
    }

    @PostMapping(value = "updatePassword")
    public Response<Boolean> forgotUpdatePassword(@RequestBody UserDto userDto) {
        return new Response<>(loginService.updatePassword(userDto.getUid(), userDto.getPassword()));
    }


}
