package com.phoenix.phoenix.web.controllers;

import com.phoenix.phoenix.data.dto.AppUserRequestDto;
import com.phoenix.phoenix.data.dto.AppUserResponseDto;
import com.phoenix.phoenix.service.user.AppUserService;
import com.phoenix.phoenix.web.exceptions.BusinessLogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody AppUserRequestDto requestDto){
        try{
            AppUserResponseDto responseDto = appUserService.createUser(requestDto);
            return ResponseEntity.ok().body(responseDto);
        } catch (BusinessLogicException exception) {
            return ResponseEntity.badRequest().body(exception);
        }
    }
}
