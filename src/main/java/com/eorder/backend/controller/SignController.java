package com.eorder.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eorder.backend.config.security.JwtTokenProvider;
import com.eorder.backend.dto.SignInResultDto;
import com.eorder.backend.dto.SignUpResultDto;
import com.eorder.backend.dto.UserDto;
import com.eorder.backend.service.SignService;

@RestController
@RequestMapping("/sign-api")
public class SignController {
    //
    private final Logger LOGGER = LoggerFactory.getLogger(SignController.class);
    private final SignService signService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SignController(JwtTokenProvider jwtTokenProvider, SignService signService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.signService = signService;
    }
    
    @PostMapping(value="/refreshTokenAndUser")
    public Object refreshTokenAndUser(@RequestBody String token) {
        //
        LOGGER.info("[refreshTokenAndUser] token 값 유효성 체크 시작!");
        if(token != null && jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOGGER.info("[refreshTokenAndUser] token 값 유효성 체크 완료");
            return authentication.getPrincipal();
        }
        return null;
    }

    @PostMapping(value = "/sign-in")
    public SignInResultDto signIn(@RequestBody UserDto user) throws RuntimeException {
        //
        LOGGER.info("[signIn] 로그인을 시도하고 있습니다. id : {}, pw : ****", user.getEmail());
        SignInResultDto signInResultDto = signService.signIn(user.getEmail(), user.getPassword());

        if (signInResultDto.getCode() == 0) {
            LOGGER.info("[signIn] 정상적으로 로그인되었습니다. id : {}, token : {}", user.getEmail(),
                signInResultDto.getToken());
        }
        return signInResultDto;
    }

    @PostMapping(value = "/sign-up")
    public SignUpResultDto signUp(@RequestBody UserDto user) {
        //
        LOGGER.info("[signUp] 회원가입을 수행합니다. id : {}, password : ****, name : {}, role : {}", user.getEmail(),
            user.getName(), user.getRole());
        SignUpResultDto signUpResultDto = signService.signUp(user.getEmail(), 
        													user.getName(),
        													user.getCompanyName(),
        													user.getCompanyAddress(),
        													user.getPhoneNumber(),
        													user.getPassword(), 
        													user.getRole());

        LOGGER.info("[signUp] 회원가입을 완료했습니다. id : {}", user.getEmail());
        return signUpResultDto;
    }

    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException {
        throw new RuntimeException("접근이 금지되었습니다.");
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String, String>> ExceptionHandler(RuntimeException e) {
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        LOGGER.error("ExceptionHandler 호출, {}, {}", e.getCause(), e.getMessage());

        Map<String, String> map = new HashMap<>();
        map.put("error type", httpStatus.getReasonPhrase());
        map.put("code", "400");
        map.put("message", "에러 발생");

        return new ResponseEntity<>(map, responseHeaders, httpStatus);
    }
}
