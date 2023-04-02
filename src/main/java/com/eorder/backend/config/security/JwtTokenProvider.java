package com.eorder.backend.config.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    //
    private final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService;
    
    @Value("${springboot.jwt.secret}")
    private String secretKey = "secretKey";
    private final long tokenValidMillisecond = 1000L * 60 * 30;
    //private final long tokenValidMillisecond = 1000L * 6;
    
    @PostConstruct
    protected void init() {
        //
        LOGGER.info("[init] JwtTokenProvider �궡 secretKey 珥덇린�솕 �떆�옉");
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        LOGGER.info("[init] JwtTokenProvider �궡 secretKey 珥덇린�솕 �셿猷�");
    }
    
    public String createToken(String userEmail, List<String> roles) {
        //
        LOGGER.info("[createToken] �넗�겙 �깮�꽦 �떆�옉");
        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("roles", roles);
        Date now = new Date();
        
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        
        LOGGER.info("[createToken] �넗�겙 �깮�꽦 �셿猷�");
        return token;
    }
    
    public Authentication getAuthentication(String token) {
        //
        LOGGER.info("[getAuthentication] �넗�겙 �씤利� �젙蹂� 議고쉶 �떆�옉");
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token));
        LOGGER.info("[getAuthentication] �넗�겙 �씤利� �젙蹂� 議고쉶 �셿猷�, UserDetails UserName : {}", userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    
    public String getUsername(String token) {
        //
        LOGGER.info("[getUsername] �넗�겙 湲곕컲 �쉶�썝 援щ퀎 �젙蹂� 異붿텧");
        String info = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        LOGGER.info("[getUsername] �넗�겙 湲곕컲 �쉶�썝 援щ퀎 �젙蹂� 異붿텧 �셿猷�, info : {}", info);
        return info;
    }
    
    public String resolveToken(HttpServletRequest request) {
        //
        LOGGER.info("[resolveToken] HTTP �뿤�뜑�뿉�꽌 Token 媛� 異붿텧");
        //return request.getHeader("Authorization");
        String jwtToken = null;
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7); // Extract the JWT token without the "Bearer " prefix
        }
        return jwtToken;
    }
    
    public boolean validateToken(String token) {
        //
        LOGGER.info("[validateToken] �넗�겙 �쑀�슚 泥댄겕 �떆�옉");
        try {
            //
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            LOGGER.info("[validateToken] �넗�겙 �쑀�슚 泥댄겕 �삁�쇅 諛쒖깮");
            return false;
        }
    }
}
