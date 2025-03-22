package com.taskmanager.task.service;

import com.taskmanager.task.TaskDTO.TaskRequest;
import com.taskmanager.task.TaskDTO.TaskResponse;
import com.taskmanager.task.entity.Task;
import com.taskmanager.task.entity.TaskStatus;
import com.taskmanager.task.entity.User;
import com.taskmanager.task.exceptions.GlobalException;
import com.taskmanager.task.exceptions.TaskException;
import com.taskmanager.task.repository.TaskRepository;
import com.taskmanager.task.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public TaskResponse createTask(TaskRequest taskRequest) {
        if (taskRequest.getAssignedTo() == null) {
            throw new GlobalException("Assigned To is a mandatory field");
        }

        Optional<User> user=userRepository.findById(taskRequest.getAssignedTo());
        if(user.isEmpty())
          throw  new TaskException("User not found with id: " + taskRequest.getAssignedTo());
        Task task = new Task();

        task.setCreatedAt(Instant.now());
        task.setUpdatedAt(Instant.now());
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(taskRequest.getStatus());
        task.setAssignedTo(user.get());

        taskRepository.save(task);
        return convertToResponse(task);
    }

    public List<TaskResponse> getAllTasks() {
        List<Task> allTasks = taskRepository.findAll();
        return allTasks.stream().map(this::convertToResponse).collect(Collectors.toList());
    }

    public TaskResponse getTaskById(Long id) {
        Optional<Task> task=taskRepository.findById(id);
        if(task.isEmpty())
            throw new TaskException("Task not found with id: " + id);
        return convertToResponse(task.get());
    }

public TaskResponse updateTaskById(Long id, TaskRequest taskRequest) {

    Optional<Task> optionalTask = taskRepository.findById(id);
    if (optionalTask.isEmpty()) {
        throw new TaskException("Task not found with id: " + id);
    }
    Task task = optionalTask.get();


    Optional<User> optionalUser = userRepository.findById(taskRequest.getAssignedTo());
    if (optionalUser.isEmpty()) {
        throw new TaskException("User not found with id: " + taskRequest.getAssignedTo());
    }
    User user = optionalUser.get();

    task.setTitle(taskRequest.getTitle());
    task.setDescription(taskRequest.getDescription());
    task.setStatus(taskRequest.getStatus());
    task.setAssignedTo(user);
    task.setUpdatedAt(Instant.now());

    taskRepository.save(task);

    return convertToResponse(task);
}

    public String deleteTaskById(Long id) {
        Optional<Task> task=taskRepository.findById(id);
        if(task.isEmpty())
            throw new TaskException("Task not found with id: " + id);


        taskRepository.deleteById(id);
        return "Task Deleted successfully";
    }


    //Additional--

    public Map<TaskStatus, List<TaskResponse>> groupTasksByStatus() {
        List<Task> allTasks = taskRepository.findAll();
        return allTasks.stream()
                .map(this::convertToResponse)
                .collect(Collectors.groupingBy(TaskResponse::getStatus));
    }

    public List<TaskResponse> filterTasksByStatus(TaskStatus status) {
        List<Task> tasks = taskRepository.findByStatus(status);
        return tasks.stream().map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getSortedTasksByCreatedAt() {
        List<Task> sortedTasks = taskRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
        return sortedTasks.stream().map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getPaginatedTasks(int page, int size) {
        Page<Task> pagedTasks = taskRepository.findAll(PageRequest.of(page, size));
        return pagedTasks.stream().map(this::convertToResponse).
                collect(Collectors.toList());
    }

    public List<TaskResponse> searchTasks(String query) {
        List<Task> tasks = taskRepository.findByTitleContainingIgnoreCase(query);
        return tasks.stream().map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private TaskResponse convertToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getAssignedTo().getId(),
                task.getAssignedTo().getTimezone()
        );
    }
}


//String userTimeZone= TimeZone.getDefault().getID();
//
//ZonedDateTime userTime=ZonedDateTime.now(ZoneId.of(userTimeZone));
//
//ZonedDateTime utcTime=userTime.withZoneSameInstant(ZoneId.of("UTC"));
//Instant utcInstant = utcTime.toInstant();
//       DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//     String utc=utcTime.format(formatter);

