package com.TaskManagement.TaskManagement.RequestDto;

import com.TaskManagement.TaskManagement.Enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class addTaskRqst {
    private  String title;
    private  String description;
    private LocalDate dueDate;
    private Status status;
    private String userMail;
}
