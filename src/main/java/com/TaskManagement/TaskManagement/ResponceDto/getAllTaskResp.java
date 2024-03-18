package com.TaskManagement.TaskManagement.ResponceDto;

import com.TaskManagement.TaskManagement.Enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class getAllTaskResp {
    private String taskId;
    private Long userId;
    private String title;
    private LocalDate dueDate;
    private Status status;
}
