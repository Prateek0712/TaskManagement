package com.TaskManagement.TaskManagement.ResponceDto;

import com.TaskManagement.TaskManagement.Enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class getTaskForUserResp {
    private String title;
    private LocalDate dueDate;
    private Status status;
}
