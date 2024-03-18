package com.TaskManagement.TaskManagement.comparators;

import com.TaskManagement.TaskManagement.ResponceDto.getAllTaskResp;

import java.time.LocalDate;
import java.util.Comparator;

public class compareByDate implements Comparator<getAllTaskResp> {
    @Override
    public int compare(getAllTaskResp task1, getAllTaskResp task2) {
        LocalDate dueDate1 = task1.getDueDate();
        LocalDate dueDate2 = task2.getDueDate();

        // Compare the due dates
        return dueDate1.compareTo(dueDate2);
    }

}
