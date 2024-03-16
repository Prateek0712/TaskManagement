package com.TaskManagement.TaskManagement.RequestDto;

import com.TaskManagement.TaskManagement.Enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class updateTaskRqst {
    private String email;
    private String password;
    private String id;
    private String title;
    private String Description;
    private LocalDate dueDate;
    private Status status;
}
