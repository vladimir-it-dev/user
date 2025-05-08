package org.example.app.service.impl;


import lombok.RequiredArgsConstructor;
import org.example.app.dto.UserRequest;
import org.example.app.dto.UserResponse;
import org.example.app.entity.User;
import org.example.app.exception.UserNotFoundByID;
import org.example.app.mapper.UserMapper;
import org.example.app.repository.UserRepository;
import org.example.app.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.example.app.exception.UserNotFoundByID.USER_NOT_FOUND_BY_ID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;


    @Override
    @Transactional
    public String createUser(UserRequest userRequest) {

        User saveUser = userRepository.save(userMapper.mapperUserRequestToUser(userRequest));
        return String.format("Пользователь с ID: %d, имя: %s, email: %s успешно сохранен в базе данных",
                saveUser.getId(),
                saveUser.getName(),
                saveUser.getEmail());

    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getInfoUser(Long idUser) {
        User user = userRepository.findWithSubscriptionsById(idUser).orElseThrow(() ->
                new UserNotFoundByID(USER_NOT_FOUND_BY_ID));
        return userMapper.mapperUserToUserResponse(user);
    }

    @Override
    @Transactional
    public String updateUser(Long idUser, UserRequest userRequest) {
        User user = userMapper.mapperUserRequestToUser(userRequest);
        user.setId(idUser);
        userRepository.save(user);
        return String.format("Пользователь с ID: %d, имя: %s, email: %s успешно обновлен в базе данных",
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    @Override
    @Transactional
    public String deleteUser(Long idUser) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new UserNotFoundByID(USER_NOT_FOUND_BY_ID));
        userRepository.delete(user);
        return String.format("Пользователь с ID: %d, имя: %s, email: %s успешно удален из базы данных",
                user.getId(),
                user.getName(),
                user.getEmail());
    }
}
