package com.taskmanager.task.controller;

import com.taskmanager.task.TaskDTO.TaskRequest;
import com.taskmanager.task.TaskDTO.TaskResponse;
import com.taskmanager.task.entity.TaskStatus;
import com.taskmanager.task.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "TaskController",description = "RestApi for Task Controller")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/tasks")


    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest taskRequest) {


        TaskResponse task = taskService.createTask(taskRequest);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<TaskResponse> allTasks = taskService.getAllTasks();
        return ResponseEntity.ok(allTasks);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Long id) {
        TaskResponse task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskResponse> updateTaskById(@PathVariable Long id, @RequestBody TaskRequest taskRequest) {
        if (taskRequest.getAssignedTo() == null) {
            throw new RuntimeException("Assigned To is a mandatory field");
        }
        TaskResponse task = taskService.updateTaskById(id, taskRequest);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable Long id) {
        String message = taskService.deleteTaskById(id);
        return ResponseEntity.ok(message);
    }

    // Additional endpoints

    @GetMapping("/tasks/grouping")
    public ResponseEntity<Map<TaskStatus, List<TaskResponse>>> groupTasksByStatus() {
        return ResponseEntity.ok(taskService.groupTasksByStatus());
    }

    @GetMapping("/tasks/filter")
    public ResponseEntity<List<TaskResponse>> filterTasksByStatus(@RequestParam TaskStatus status) {
        return ResponseEntity.ok(taskService.filterTasksByStatus(status));
    }

    @GetMapping("/tasks/sorting")
    public ResponseEntity<List<TaskResponse>> getSortedTasksByCreatedAt() {
        return ResponseEntity.ok(taskService.getSortedTasksByCreatedAt());
    }

    @GetMapping("/tasks/pagination")
    public ResponseEntity<List<TaskResponse>> getPaginatedTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(taskService.getPaginatedTasks(page, size));
    }

    @GetMapping("/tasks/search")
    public ResponseEntity<List<TaskResponse>> searchTasks(@RequestParam String query) {
        return ResponseEntity.ok(taskService.searchTasks(query));
    }
}