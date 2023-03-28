package com.eorder.backend.service;

import com.eorder.backend.dto.SignInResultDto;
import com.eorder.backend.dto.SignUpResultDto;

public interface SignService {
    //
    SignUpResultDto signUp(String email, 
    						String name, 
    						String companyName, 
    						String companyAddress, 
    						String phoneNumber, 
    						String password, 
    						String role);
    SignInResultDto signIn(String id, String password) throws RuntimeException;
}
