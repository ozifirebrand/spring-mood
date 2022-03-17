package com.phoenix.phoenix.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenix.phoenix.data.models.AppUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

//    @Autowired
    private final AuthenticationManager authenticationManager;

    private final ObjectMapper mapper ;
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
        this.mapper = new ObjectMapper();
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        //Read credentials from http request
        try {
            AppUser user = mapper.readValue(request.getInputStream(), AppUser.class);
            //Create username password token
            UsernamePasswordAuthenticationToken token = new
                    UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

            //Pass token to the authentication manager

            return authenticationManager.authenticate(token);
        }catch (IOException exception){
            log.info(exception.getMessage());
        }
        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
    HttpServletResponse response, FilterChain chain, Authentication authResult){

        String jwtToken = JWT.create().withSubject(((User)authResult.getPrincipal())
                        .getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() +864_000))
                .sign(Algorithm.HMAC256("mysecretCode8573@!".getBytes()));
        response.addHeader("Authorization", jwtToken);
    }

//    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if ( header == null || !header.startsWith("Bearer") ){
            chain.doFilter(request, response);
        return;
        }

        SecurityContextHolder.getContext().setAuthentication(getAuthentication(request));
        super.doFilter(request, response, chain);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String header = request.getHeader("Authorization");

        if ( header != null ){
            String username = JWT.require(Algorithm.HMAC256("mysecretCode8573@!"))
                    .build().verify(header.replace("Bearer",
                            "")).getSubject();

            if ( username != null ) {

                return new UsernamePasswordAuthenticationToken(username,
                        null, new ArrayList<>());
            }
        }
        return null;
    }
}
  