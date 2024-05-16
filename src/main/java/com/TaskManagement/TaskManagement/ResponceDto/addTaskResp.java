package com.TaskManagement.TaskManagement.ResponceDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class addTaskResp {
    private String id;
    private String title;
    private LocalDate dueDate;
    private String userMail;
    private String userName;
}
