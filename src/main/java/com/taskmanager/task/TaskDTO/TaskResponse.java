package com.taskmanager.task.TaskDTO;

import com.taskmanager.task.entity.TaskStatus;
import lombok.*;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private Instant createdAt;
    private Instant updatedAt;
    private Long assignedTo;
    private String timezone;
}