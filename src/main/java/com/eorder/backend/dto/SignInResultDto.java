package com.eorder.backend.dto;

import com.eorder.backend.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SignInResultDto extends SignUpResultDto {
    //
    private String token;
    private User user;
    
    public SignInResultDto(boolean success, int code, String msg, User user) {
        super(success, code, msg);
        this.token = token;
        this.user = user;
    }
}
