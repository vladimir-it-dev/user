package org.example.app.mapper;

import org.example.app.dto.UserRequest;
import org.example.app.dto.UserResponse;
import org.example.app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = SubscriptionMapper.class)
public interface UserMapper {

    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "id", ignore = true)
    User mapperUserRequestToUser(UserRequest userRequest);

    @Mapping(target = "subscriptions", source = "subscriptions")
    UserResponse mapperUserToUserResponse(User user);

}
