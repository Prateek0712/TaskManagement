package com.TaskManagement.TaskManagement.Service;

import com.TaskManagement.TaskManagement.Enity.Task;
import com.TaskManagement.TaskManagement.Enity.User;
import com.TaskManagement.TaskManagement.Exceptions.TaskNotFoundException;
import com.TaskManagement.TaskManagement.Exceptions.UserNotFoundException;
import com.TaskManagement.TaskManagement.Reposiotory.TaskRepo;
import com.TaskManagement.TaskManagement.Reposiotory.UserRepo;
import com.TaskManagement.TaskManagement.RequestDto.addTaskRqst;
import com.TaskManagement.TaskManagement.RequestDto.updateTaskRqst;
import com.TaskManagement.TaskManagement.ResponceDto.addTaskResp;
import com.TaskManagement.TaskManagement.ResponceDto.getTaskForUserResp;
import com.TaskManagement.TaskManagement.ResponceDto.updateTaskResp;
import com.TaskManagement.TaskManagement.Transformers.TaskTranformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        System.out.println(user.getTaskList().size());
        return addTaskResp;
    }
    public List<getTaskForUserResp> getAllTaskForUser(String email) throws Exception
    {
        Optional<User> optionalUser=userRepo.findByEmail(email);
        if(optionalUser.isEmpty())
        {
            throw new UserNotFoundException("USER  NOT  EXIST");
        }
        User user =optionalUser.get();
        List<Task> taskList=user.getTaskList();
        List<getTaskForUserResp> taskAndDueDatesList=new ArrayList<>();
        System.out.println(taskAndDueDatesList.size()+" size");
        for(Task t:taskList)
        {
            getTaskForUserResp g=new getTaskForUserResp();
            g.setTitle(t.getTitle());
            g.setDueDate(t.getDueDate());
            g.setStatus(t.getStatus());
            taskAndDueDatesList.add(g);
        }
        return taskAndDueDatesList;
    }

    public updateTaskResp updateTask (updateTaskRqst updatedTask) throws Exception
    {
        Optional<User>optionalUser=userRepo.findByEmail(updatedTask.getEmail());
        Optional<Task>optionalTask=taskRepo.findById(updatedTask.getId()); //this is task id
        if(optionalUser.isEmpty())
        {
            throw new Exception("USER NOT FOUND");
        }
        if(optionalTask.isEmpty())
        {
            throw new TaskNotFoundException("GIVEN TASK DOES NOT EXIST");
        }
        Task task=optionalTask.get();
        if(updatedTask.getTitle().length()!=0)
        {
            task.setTitle(updatedTask.getTitle());
        }
        if(updatedTask.getDescription()!=null)
        {
            task.setDescription(updatedTask.getDescription());
        }
        if (updatedTask.getStatus()!=null)
        {
            task.setStatus(updatedTask.getStatus());
        }
        if(updatedTask.getDueDate()!=null)
        {
            task.setDueDate(updatedTask.getDueDate());
        }
        task= taskRepo.save(task);
        updateTaskResp t=new updateTaskResp();
        t.setTitle(task.getTitle());
        t.setDueDate(task.getDueDate());
        t.setUserMail(optionalUser.get().getEmail());
        t.setStatus(task.getStatus());
        return t;
    }

    public String deleteTaskById(String taskId) throws Exception
    {
        Optional<Task> optionalTask=taskRepo.findById(taskId);
        if(optionalTask.isEmpty())
        {
            throw new TaskNotFoundException("Task With Given ID not found");
        }
        taskRepo.deleteById(taskId);
        return "Task with ID: "+taskId+" is deleted successfully";
    }
}
