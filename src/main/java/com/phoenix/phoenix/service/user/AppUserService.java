package com.phoenix.phoenix.service.user;

import com.phoenix.phoenix.data.dto.AppUserRequestDto;
import com.phoenix.phoenix.data.dto.AppUserResponseDto;
import com.phoenix.phoenix.web.exceptions.BusinessLogicException;

public interface AppUserService {

    AppUserResponseDto createUser(AppUserRequestDto userRequestDto) throws BusinessLogicException;
}
