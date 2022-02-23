package com.phoenix.phoenix.service.user;

import com.phoenix.phoenix.data.dto.AppUserRequestDto;
import com.phoenix.phoenix.data.dto.AppUserResponseDto;
import com.phoenix.phoenix.data.models.AppUser;
import com.phoenix.phoenix.data.repository.AppUserRepository;
import com.phoenix.phoenix.web.exceptions.BusinessLogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService{
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    AppUserRepository appUserRepository;
    @Override
    public AppUserResponseDto createUser
            (AppUserRequestDto userRequestDto) throws BusinessLogicException {
        //Check if user exists
        Optional<AppUser> optionalAppUser =
                appUserRepository.findByEmail(userRequestDto.getEmail());
        if ( optionalAppUser.isPresent() )
            throw new BusinessLogicException("This user is present");
        //Create an app user object
        AppUser appUser = new AppUser();
        appUser.setFirstName(userRequestDto.getFirstName());
        appUser.setLastName(userRequestDto.getLastName());
        appUser.setEmail(userRequestDto.getEmail());
        appUser.setAddress(userRequestDto.getAddress());
        appUser.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        //Save object
        appUserRepository.save(appUser);

        //return response
        return buildResponse(appUser);
    }

    private AppUserResponseDto buildResponse(AppUser appUser){
        return AppUserResponseDto.builder()
                .address(appUser.getAddress())
                .email(appUser.getEmail())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName()).build();
    }
}
