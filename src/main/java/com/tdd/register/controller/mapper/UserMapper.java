package com.tdd.register.controller.mapper;

import com.tdd.register.controller.request.UserRequest;
import com.tdd.register.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toUser(UserRequest userRequest);
}
