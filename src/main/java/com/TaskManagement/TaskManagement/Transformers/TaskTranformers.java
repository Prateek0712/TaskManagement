package com.TaskManagement.TaskManagement.Transformers;

import com.TaskManagement.TaskManagement.Enity.Task;
import com.TaskManagement.TaskManagement.RequestDto.addTaskRqst;

public class TaskTranformers {

    public static Task addTaskRqstToTask(addTaskRqst taskAddingRqst)
    {
        Task task= Task.builder()
                .title(taskAddingRqst.getTitle())
                .description(taskAddingRqst.getDescription())
                .dueDate(taskAddingRqst.getDueDate())
                .status(taskAddingRqst.getStatus())
                .build();
        return task;
    }
}
