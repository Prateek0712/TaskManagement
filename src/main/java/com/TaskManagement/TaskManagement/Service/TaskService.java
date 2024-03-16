package com.TaskManagement.TaskManagement.Service;

import com.TaskManagement.TaskManagement.Enity.Task;
import com.TaskManagement.TaskManagement.Enity.User;
import com.TaskManagement.TaskManagement.Exceptions.UserNotFoundException;
import com.TaskManagement.TaskManagement.Reposiotory.TaskRepo;
import com.TaskManagement.TaskManagement.Reposiotory.UserRepo;
import com.TaskManagement.TaskManagement.RequestDto.addTaskRqst;
import com.TaskManagement.TaskManagement.ResponceDto.addTaskResp;
import com.TaskManagement.TaskManagement.Transformers.TaskTranformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TaskRepo taskRepo;
    public addTaskResp addTask(addTaskRqst taskAddingRqst) throws Exception
    {
        Optional<User> optionalUser= userRepo.findByEmail(taskAddingRqst.getUserMail());
        if(optionalUser.isEmpty())
        {
            throw  new UserNotFoundException("USER WITH  THIS TASK NOT EXIST IN SYSTEM");
        }
        User user=optionalUser.get();
        Task task = TaskTranformers.addTaskRqstToTask(taskAddingRqst);
        task.setUser(user);
        user.getTaskList().add(task);
        task=taskRepo.save(task);
        userRepo.save(user);
        addTaskResp addTaskResp=new addTaskResp();
        addTaskResp.setId(task.getTaskId());
        addTaskResp.setTitle(task.getTitle());
        addTaskResp.setDueDate(task.getDueDate());
        addTaskResp.setUserMail(user.getEmail());
        addTaskResp.setUserName(user.getUserName());
        return addTaskResp;
    }
}
