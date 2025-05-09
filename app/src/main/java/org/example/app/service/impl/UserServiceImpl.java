package org.example.app.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.app.dto.UserRequest;
import org.example.app.dto.UserResponse;
import org.example.app.entity.User;
import org.example.app.exception.UserExistsByEmailException;
import org.example.app.exception.UserNotFoundByIDException;
import org.example.app.mapper.UserMapper;
import org.example.app.repository.UserRepository;
import org.example.app.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.example.app.exception.UserExistsByEmailException.USER_EXISTS_BY_EMAIL_EXCEPTION;
import static org.example.app.exception.UserNotFoundByIDException.USER_NOT_FOUND_BY_ID_EXCEPTION;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String USER_EXISTS_BY_EMAIL_TO_DB = "Пользователь с таким email: {} уже существует в базе данных";

    private final UserRepository userRepository;

    private final UserMapper userMapper;


    @Override
    @Transactional
    public String createUser(UserRequest userRequest) {

        // Проверка наличия пользователя с таким email в базе данных
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            log.error(USER_EXISTS_BY_EMAIL_TO_DB, userRequest.getEmail());
            throw new UserExistsByEmailException(USER_EXISTS_BY_EMAIL_EXCEPTION);
        }
        // Сохранение пользователя в базе данных
        User saveUser = userRepository.save(userMapper.mapperUserRequestToUser(userRequest));
        log.info("Пользователь с ID: {}, имя: {}, email: {} успешно сохранен в базе данных",
                saveUser.getId(),
                saveUser.getName(),
                saveUser.getEmail());
        return String.format("Пользователь с ID: %d, имя: %s, email: %s успешно сохранен в базе данных",
                saveUser.getId(),
                saveUser.getName(),
                saveUser.getEmail());

    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getInfoUser(Long idUser) {

        // Получение пользователя по ID из базы данных
        User user = userRepository.findWithSubscriptionsById(idUser).orElseThrow(() ->
                new UserNotFoundByIDException(USER_NOT_FOUND_BY_ID_EXCEPTION));
        log.info("Пользователь с ID: {}, имя: {}, email: {} успешно найден в базе данных",
                user.getId(),
                user.getName(),
                user.getEmail());
        return userMapper.mapperUserToUserResponse(user);
    }

    @Override
    @Transactional
    public String updateUser(Long idUser, UserRequest userRequest) {

        // Получение пользователя по ID из базы данных
        User user = userRepository.findById(idUser).orElseThrow(() ->
                new UserNotFoundByIDException(USER_NOT_FOUND_BY_ID_EXCEPTION));

        // Проверка наличия пользователя с таким email в базе данных
        Optional<User> userRepositoryByEmail = userRepository.findByEmail(userRequest.getEmail());

        if (userRepositoryByEmail.isPresent() && !userRepositoryByEmail.get().getId().equals(user.getId())) {
            log.error(USER_EXISTS_BY_EMAIL_TO_DB, userRequest.getEmail());
            throw new UserExistsByEmailException(USER_EXISTS_BY_EMAIL_EXCEPTION);
        }
        // Обновление данных пользователя
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        userRepository.save(user);
        log.info("Пользователь с ID: {}, имя: {}, email: {} успешно обновлен в базе данных",
                user.getId(),
                user.getName(),
                user.getEmail());
        return String.format("Пользователь с ID: %d, имя: %s, email: %s успешно обновлен в базе данных",
                user.getId(),
                user.getName(),
                user.getEmail());
    }

    @Override
    @Transactional
    public String deleteUser(Long idUser) {

        // Получение пользователя по ID из базы данных
        User user = userRepository.findById(idUser).orElseThrow(() ->
                new UserNotFoundByIDException(USER_NOT_FOUND_BY_ID_EXCEPTION));
        // Удаление пользователя из базы данных
        userRepository.delete(user);
        log.info("Пользователь с ID: {}, имя: {}, email: {} успешно удален из базы данных",
                user.getId(),
                user.getName(),
                user.getEmail());
        return String.format("Пользователь с ID: %d, имя: %s, email: %s успешно удален из базы данных",
                user.getId(),
                user.getName(),
                user.getEmail());
    }
}
