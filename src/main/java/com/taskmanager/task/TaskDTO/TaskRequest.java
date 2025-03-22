package com.taskmanager.task.TaskDTO;

import com.taskmanager.task.entity.TaskStatus;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private Long assignedTo;
}