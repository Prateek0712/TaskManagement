package com.TaskManagement.TaskManagement.RequestDto;

import com.TaskManagement.TaskManagement.Enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class sortAndFilterRqst {
    private boolean sortByDate;
    private boolean sortByStatus;

    private Status filterByStasus;

    private LocalDate filterByDueDate;

}
