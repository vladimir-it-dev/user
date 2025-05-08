package org.example.app.service;

import org.example.app.dto.UserRequest;
import org.example.app.dto.UserResponse;

public interface UserService {

    String createUser(UserRequest userRequest);

    UserResponse getInfoUser(Long idUser);

    String updateUser(Long idUser, UserRequest userRequest);

    String deleteUser(Long idUser);
}
