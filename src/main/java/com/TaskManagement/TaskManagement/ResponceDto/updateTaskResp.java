package com.TaskManagement.TaskManagement.ResponceDto;

import com.TaskManagement.TaskManagement.Enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class updateTaskResp {
    private String title;
    private LocalDate dueDate;
    private String userMail;
    private Status status;
}
