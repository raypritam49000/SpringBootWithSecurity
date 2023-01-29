package com.rest.web.api.service;
import com.rest.web.api.dto.UserDto;

import java.util.Optional;

public interface IUserService{
    public UserDto createUser(UserDto userDto);
    public Optional<UserDto> findByUsername(String username);
    public boolean existsByUsername(String username);
}
