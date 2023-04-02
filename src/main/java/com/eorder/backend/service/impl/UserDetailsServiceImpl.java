package com.eorder.backend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eorder.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    //
    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //
        LOGGER.info("[loadUserByUsername] loadUserByUsrname �닔�뻾. username : {}", username);
        return userRepository.getByEmail(username);
    }

}
