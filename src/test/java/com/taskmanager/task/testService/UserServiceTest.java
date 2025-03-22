package com.taskmanager.task.testService;


import com.taskmanager.task.TaskDTO.UserRequest;
import com.taskmanager.task.TaskDTO.UserResponse;
import com.taskmanager.task.entity.User;
import com.taskmanager.task.exceptions.TaskException;
import com.taskmanager.task.repository.UserRepository;
import com.taskmanager.task.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User createUser(Long id,String firstName,String lastName,String timezone,String email,Boolean isActive)
    {
          User user=new User();
          user.setId(id);
          user.setFirstName(firstName);
          user.setLastName(lastName);
          user.setEmail(email);
          user.setTimezone(timezone);
          user.setActive(isActive);

          return user;
    }

    @Test
    public void createUserTest() {

        UserRequest userRequest = new UserRequest("John", "Doe", true, "johndoe@gmail.com", "UTC");

        User user = createUser(1L, "John", "Doe", "UTC", "johndoe@gmail.com", true);
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserResponse userResponse = userService.createUser(userRequest);
        assertNotNull(userResponse);

        assertEquals("johndoe@gmail.com", userResponse.getEmail());

        verify(userRepository).save(any(User.class));
    }

    @Test

    public void getAllUsersTest()
    {
        User user1 = createUser(1L, "John", "Doe", "UTC", "johndoe@gmail.com", true);
        User user2 = createUser(1L, "John", "Smith", "UTC", "johnsmith@gmail.com", true);

        List<User> usersList=List.of(user1,user2);

        when(userRepository.findAll()).thenReturn(usersList);

        List<UserResponse> responseList = userService.getAllUsers();
        assertEquals(2,responseList.size());
        verify(userRepository).findAll();
    }

    @Test
    public void getUserByIdTest()
    {
        User user = createUser(1L, "John", "Doe", "UTC", "johndoe@gmail.com", true);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponse userResponse=userService.getUserById(1L);
        assertEquals("johndoe@gmail.com",userResponse.getEmail());
        verify(userRepository).findById(1L);
    }


    @Test
    public void updateUserByIdTest() {
        Long userId = 1L;
        User existingUser = createUser(userId, "John", "Doe", "UTC", "johndoe@gmail.com", true);

        UserRequest updatedRequest = new UserRequest("John", "Smith", true, "johnsmith@gmail.com", "PST");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        User updatedUser = createUser(userId, "John", "Smith", "UTC", "johnsmith@gmail.com", true);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserResponse response = userService.updateUserById(userId, updatedRequest);

        assertNotNull(response);
        assertEquals("John", response.getFirstName());
        assertEquals("Smith", response.getLastName());
        assertEquals("UTC", response.getTimezone());
        assertEquals("johnsmith@gmail.com", response.getEmail());
        assertEquals(true, response.isActive());

        verify(userRepository).findById(userId);
        verify(userRepository).save(any(User.class));
    }

    @Test
   public void deleteUserByIdTest()
   {
       when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

       String result=userService.deleteUserById(1L);

       assertEquals("User deleted successfully",result);
       verify(userRepository).findById(1L);
   }


    @Test
    public void deleteUserByIdNotFoundTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskException.class, () -> userService.deleteUserById(1L));
        verify(userRepository).findById(1L);
    }

}
