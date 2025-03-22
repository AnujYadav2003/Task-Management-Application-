package com.taskmanager.task.TaskDTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {

    private String firstName;
    private String lastName;
    @JsonProperty("isActive")
    private boolean isActive;
    private String email;
    private String timezone;




}
