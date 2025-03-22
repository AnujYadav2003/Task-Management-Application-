package com.taskmanager.task.service;
import com.taskmanager.task.TaskDTO.UserRequest;
import com.taskmanager.task.TaskDTO.UserResponse;
import com.taskmanager.task.entity.User;
import com.taskmanager.task.exceptions.GlobalException;
import com.taskmanager.task.exceptions.TaskException;
import com.taskmanager.task.repository.TaskRepository;
import com.taskmanager.task.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public UserResponse createUser(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new GlobalException("Email already exists!");
        }
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setActive(userRequest.isActive());
        user.setEmail(userRequest.getEmail());
        user.setTimezone(userRequest.getTimezone());
        user = userRepository.save(user);
        return convertToResponse(user);
    }
    public List<UserResponse> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return allUsers.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public UserResponse getUserById(Long id) {

        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty())
            throw new TaskException("User not found with id: " + id);
        return convertToResponse(user.get());
    }

    public String deleteUserById(Long id) {
        Optional<User> user=userRepository.findById(id);
        if(user.isEmpty())
            throw new TaskException("User not found with id: " + id);
        userRepository.deleteById(id);
        return "User deleted successfully";
    }
     public UserResponse updateUserById(Long id,UserRequest userRequest)
     {
         Optional<User> user=userRepository.findById(id);
         if(user.isEmpty())
             throw new TaskException("User not found with id: " + id);

         User newUser=new User();
         newUser.setFirstName(userRequest.getFirstName());
         newUser.setLastName(userRequest.getLastName());
         newUser.setTimezone(userRequest.getTimezone());
         newUser.setEmail(userRequest.getEmail());
         newUser.setActive(userRequest.isActive());

           userRepository.save(newUser);
         return convertToResponse(newUser);

     }
    private UserResponse convertToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getTimezone(),
                user.isActive()
        );
    }
}