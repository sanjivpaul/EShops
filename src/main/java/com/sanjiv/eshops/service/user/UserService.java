package com.sanjiv.eshops.service.user;

import com.sanjiv.eshops.exception.AlreadyExistsException;
import com.sanjiv.eshops.exception.ResourceNotFoundException;
import com.sanjiv.eshops.model.User;
import com.sanjiv.eshops.repository.UserRepository;
import com.sanjiv.eshops.request.CreateUserRequest;
import com.sanjiv.eshops.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not foy]und!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user-> !userRepository.existsByEmail(request.getEmail())) //if user exist with that email throw error
                .map(req ->{
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());

                    return userRepository.save(user);

                }).orElseThrow(()-> new AlreadyExistsException("Oops! "+ request.getEmail()+" already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
//        if user exist then update
        return userRepository.findById(userId).map(existingUser ->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(()->
              new  ResourceNotFoundException("User not found!"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, ()->{
            throw new ResourceNotFoundException("User not found!");
        });

    }
}
