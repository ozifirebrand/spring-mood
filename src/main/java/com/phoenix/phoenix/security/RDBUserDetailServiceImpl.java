package com.phoenix.phoenix.security;

import com.phoenix.phoenix.data.models.AppUser;
import com.phoenix.phoenix.data.models.Authority;
import com.phoenix.phoenix.data.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RDBUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        // Query for user details form db
        AppUser user = appUserRepository.findByEmail(username).orElse(null);
        //Check that user exists
        if ( user == null ){
            throw new UsernameNotFoundException
                    ("User with email: " +username + " does not exist");
        }
        //return UserDetails
        return new User(user.getEmail(), user.getPassword(), getAuthorities(user.getAuthorities()));
    }

    private List<SimpleGrantedAuthority> getAuthorities(List<Authority> authorities){
        return authorities.stream().map(authority -> {
            return new SimpleGrantedAuthority
                    (authority.name());
        }).collect(Collectors.toList());
    }
}
