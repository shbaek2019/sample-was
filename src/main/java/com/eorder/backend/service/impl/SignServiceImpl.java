package com.eorder.backend.service.impl;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eorder.backend.common.CommonResponse;
import com.eorder.backend.config.security.JwtTokenProvider;
import com.eorder.backend.dto.SignInResultDto;
import com.eorder.backend.dto.SignUpResultDto;
import com.eorder.backend.entity.User;
import com.eorder.backend.repository.UserRepository;
import com.eorder.backend.service.SignService;

@Service
public class SignServiceImpl implements SignService {
    //
    private final Logger LOGGER = LoggerFactory.getLogger(SignServiceImpl.class);

    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;
    public PasswordEncoder passwordEncoder;
    
    @Autowired
    public SignServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public SignUpResultDto signUp(String email, 
									String name, 
									String companyName, 
									String companyAddress, 
									String phoneNumber, 
									String password, 
									String role) {
        //
        LOGGER.info("[getSignUpResult] 회원 가입 정보 전달");
        User user;
        if(role.equalsIgnoreCase("admin")) {
            user = User.builder()
            		.email(email)
                    .name(name)
                    .companyName(companyName)
                    .companyAddress(companyAddress)
                    .phoneNumber(phoneNumber)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();
        } else {
            user = User.builder()
            		.email(email)
                    .name(name)
                    .companyName(companyName)
                    .companyAddress(companyAddress)
                    .phoneNumber(phoneNumber)
                    .password(passwordEncoder.encode(password))
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }
        
        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new SignInResultDto();
        
        LOGGER.info("[getSignUpResult] userEntity 값이 들어왔는지 확인 후 결과값 주입");
        if(!savedUser.getName().isEmpty()) {
            LOGGER.info("[getSignUpResult] 정상 처리 완료");
            setSuccessResult(signUpResultDto);
        }
        return null;
    }

    @Override
    public SignInResultDto signIn(String id, String password) throws RuntimeException {
        LOGGER.info("[getSignInResult] signDataHandler 로 회원 정보 요청");
        User user = userRepository.getByEmail(id);
        
        LOGGER.info("[getSignInResult] Id : {}", id);

        LOGGER.info("[getSignInResult] 패스워드 비교 수행");
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException();
        }
        LOGGER.info("[getSignInResult] 패스워드 일치");
        
        // dto에 넘겨줄 user에서 password 정보 제거
        if(user != null) {
            user.setPassword(null);
        }

        LOGGER.info("[getSignInResult] SignInResultDto 객체 생성");
        SignInResultDto signInResultDto = SignInResultDto.builder()
            .token(jwtTokenProvider.createToken(String.valueOf(user.getUid()),
                user.getRoles()))
            .user(user)
            .build();

        LOGGER.info("[getSignInResult] SignInResultDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }
    
    private void setSuccessResult(SignUpResultDto result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
    
    private void setFailResult(SignUpResultDto result) {
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }
    
}
