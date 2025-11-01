package com.sanjiv.eshops.service.user;

import com.sanjiv.eshops.model.User;
import com.sanjiv.eshops.request.CreateUserRequest;
import com.sanjiv.eshops.request.UserUpdateRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
}
