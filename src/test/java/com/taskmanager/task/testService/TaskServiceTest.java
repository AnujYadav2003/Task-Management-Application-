package com.taskmanager.task.testService;

import com.taskmanager.task.TaskDTO.TaskRequest;
import com.taskmanager.task.TaskDTO.TaskResponse;
import com.taskmanager.task.entity.Task;
import com.taskmanager.task.entity.TaskStatus;
import com.taskmanager.task.entity.User;
import com.taskmanager.task.exceptions.TaskException;
import com.taskmanager.task.repository.TaskRepository;
import com.taskmanager.task.repository.UserRepository;
import com.taskmanager.task.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    private Task createTask(Long id, String title, String description, TaskStatus status, User user) {
        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setAssignedTo(user);
        return task;
    }

    @Test
    public void createTaskTest() {
        TaskRequest request = new TaskRequest("Task 1", "Description 1", TaskStatus.PENDING, 1L);
        User user = new User();
        user.setId(1L);
        user.setTimezone("UTC");
        Task task = createTask(1L, request.getTitle(), request.getDescription(), request.getStatus(), user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse response = taskService.createTask(request);

        assertNotNull(response);
        assertEquals("Task 1", response.getTitle());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    public void getAllTasksTest() {
        User user = new User();
        user.setId(1L);
        user.setTimezone("UTC");

        List<Task> taskList = List.of(
                createTask(1L, "Task 1", "Description 1", TaskStatus.PENDING, user),
                createTask(2L, "Task 2", "Description 2", TaskStatus.IN_PROGRESS, user)
        );

        when(taskRepository.findAll()).thenReturn(taskList);

        List<TaskResponse> tasks = taskService.getAllTasks();

        assertEquals(2, tasks.size());
        assertEquals("Task 1", tasks.get(0).getTitle());
        assertEquals("Task 2", tasks.get(1).getTitle());
        verify(taskRepository).findAll();
    }


    @Test
    public void getTaskByIdTest() {
        Task task = createTask(1L, "Task 1", "Desc", TaskStatus.PENDING, new User());

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponse response = taskService.getTaskById(1L);

        assertNotNull(response);
        assertEquals("Task 1", response.getTitle());
        verify(taskRepository).findById(1L);
    }

    @Test
    public void getTaskByIdNotFoundTest() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskException.class, () -> taskService.getTaskById(1L));
        verify(taskRepository).findById(1L);
    }

    @Test
    public void updateTaskByIdTest() {

        User user = new User();
        user.setId(1L);

        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setTitle("Old Task");
        existingTask.setStatus(TaskStatus.PENDING);
        existingTask.setAssignedTo(user);

        TaskRequest taskRequest = new TaskRequest("Updated Task", "Updated Desc", TaskStatus.IN_PROGRESS, 1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);

        TaskResponse response = taskService.updateTaskById(1L, taskRequest);

        assertEquals("Updated Task", response.getTitle());
        assertEquals(TaskStatus.IN_PROGRESS, response.getStatus());
        verify(taskRepository).save(existingTask);
    }

    @Test
    public void deleteTaskByIdTest() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(new Task()));

        String result = taskService.deleteTaskById(1L);

        assertEquals("Task Deleted successfully", result);
        verify(taskRepository).deleteById(1L);
    }

    @Test
    public void deleteTaskByIdNotFoundTest() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TaskException.class, () -> taskService.deleteTaskById(1L));
        verify(taskRepository).findById(1L);
    }
}
