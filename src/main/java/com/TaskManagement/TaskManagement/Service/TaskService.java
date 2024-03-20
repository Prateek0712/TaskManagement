package com.TaskManagement.TaskManagement.Service;


import com.TaskManagement.TaskManagement.Enity.Task;
import com.TaskManagement.TaskManagement.Enity.User;
import com.TaskManagement.TaskManagement.Enums.Status;
import com.TaskManagement.TaskManagement.Exceptions.TaskNotFoundException;
import com.TaskManagement.TaskManagement.Exceptions.UserNotFoundException;
import com.TaskManagement.TaskManagement.Reposiotory.TaskRepo;
import com.TaskManagement.TaskManagement.Reposiotory.UserRepo;
import com.TaskManagement.TaskManagement.RequestDto.addTaskRqst;
import com.TaskManagement.TaskManagement.RequestDto.sortAndFilterRqst;
import com.TaskManagement.TaskManagement.RequestDto.updateTaskRqst;
import com.TaskManagement.TaskManagement.ResponceDto.addTaskResp;
import com.TaskManagement.TaskManagement.ResponceDto.getAllTaskResp;
import com.TaskManagement.TaskManagement.ResponceDto.updateTaskResp;
import com.TaskManagement.TaskManagement.Transformers.TaskTranformers;
import com.TaskManagement.TaskManagement.comparators.compareByDate;
import com.TaskManagement.TaskManagement.comparators.compareByStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.JstlUtils;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

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

    public updateTaskResp updateTask (updateTaskRqst updatedTask) throws Exception
    {
        Optional<User>optionalUser=userRepo.findByEmail(updatedTask.getEmail());
        Optional<Task>optionalTask=taskRepo.findById(updatedTask.getId()); //this is task id
        if(optionalUser.isEmpty())
        {
            throw new UserNotFoundException("USER NOT FOUND");
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

    public  List<getAllTaskResp> getAllTasks(String email)
    {
        List<Task>taskList=taskRepo.findAll();
        List<getAllTaskResp>allTaskList=new ArrayList<>();
        User user= userRepo.findByEmail(email).get();
        if((user.getRol()+"").equals("ADMIN"))
        {
            for(Task t:taskList)
            {
                getAllTaskResp gt=new getAllTaskResp();
                gt.setTaskId(t.getTaskId());
                gt.setUserId(t.getUser().getId());
                gt.setTitle(t.getTitle());
                gt.setDueDate(t.getDueDate());
                gt.setStatus(t.getStatus());
                allTaskList.add(gt);
            }
        }
        else
        {
            for(Task t:taskList)
            {
                if(t.getUser().getId()==user.getId())
                {
                    getAllTaskResp gt=new getAllTaskResp();
                    gt.setTaskId(t.getTaskId());
                    gt.setUserId(t.getUser().getId());
                    gt.setTitle(t.getTitle());
                    gt.setDueDate(t.getDueDate());
                    gt.setStatus(t.getStatus());
                    allTaskList.add(gt);
                }
            }
        }
        System.out.println(allTaskList.size()+" -----> this is size");
        return allTaskList;
    }

    //sorting and  filtering for user
    public List<getAllTaskResp> sortAndFilter(sortAndFilterRqst rqst, String email)
    {

        List<getAllTaskResp>taskList=getAllTasks(email);
       // sorting
        boolean date =rqst.isSortByDate();
        boolean statuss=rqst.isSortByStatus();
        if(date)
        {
            Collections.sort(taskList,new compareByDate());
        }
        else if(statuss)
        {
            Collections.sort(taskList,new compareByStatus());
        }
        if(rqst.getFilterByStasus()!=null|| rqst.getFilterByDueDate()!=null)
        {
            taskList=filter(taskList,rqst.getFilterByStasus(),rqst.getFilterByDueDate());
        }
        return new ArrayList<>(taskList);

    }
    public List<getAllTaskResp> filter(List<getAllTaskResp>taskList,Status  status,LocalDate dueDate)
    {
        List<getAllTaskResp>filteredList=new ArrayList<>();
        if(status!=null && dueDate==null)
        {
            for(getAllTaskResp gt:taskList)
            {
                if(status.equals(gt.getStatus()))
                {
                    filteredList.add(gt);
                }
            }
        }
        else if(status==null && dueDate!=null)
        {
            for(getAllTaskResp gt:taskList)
            {
                if(dueDate.equals(gt.getDueDate()))
                {
                    filteredList.add(gt);
                }
            }
        }
        else if(status!=null && dueDate!=null)
        {
            for(getAllTaskResp gt:taskList)
            {
                if(dueDate.equals(gt.getDueDate()) && status.equals(gt.getStatus()))
                {
                    filteredList.add(gt);
                }
            }
        }
        return new ArrayList<>(filteredList);
    }
}










