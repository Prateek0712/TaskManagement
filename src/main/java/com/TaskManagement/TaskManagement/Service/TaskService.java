package com.TaskManagement.TaskManagement.Service;

import com.TaskManagement.TaskManagement.Enity.Admin;
import com.TaskManagement.TaskManagement.Enity.Task;
import com.TaskManagement.TaskManagement.Enity.User;
import com.TaskManagement.TaskManagement.Enums.Status;
import com.TaskManagement.TaskManagement.Exceptions.AdminNotExistException;
import com.TaskManagement.TaskManagement.Exceptions.NoFilteringParameterException;
import com.TaskManagement.TaskManagement.Exceptions.TaskNotFoundException;
import com.TaskManagement.TaskManagement.Exceptions.UserNotFoundException;
import com.TaskManagement.TaskManagement.Reposiotory.AdminRepo;
import com.TaskManagement.TaskManagement.Reposiotory.TaskRepo;
import com.TaskManagement.TaskManagement.Reposiotory.UserRepo;
import com.TaskManagement.TaskManagement.RequestDto.addTaskRqst;
import com.TaskManagement.TaskManagement.RequestDto.updateTaskRqst;
import com.TaskManagement.TaskManagement.ResponceDto.addTaskResp;
import com.TaskManagement.TaskManagement.ResponceDto.getAllTaskResp;
import com.TaskManagement.TaskManagement.ResponceDto.getTaskForUserResp;
import com.TaskManagement.TaskManagement.ResponceDto.updateTaskResp;
import com.TaskManagement.TaskManagement.Transformers.TaskTranformers;
import com.TaskManagement.TaskManagement.comparators.compareByDate;
import com.TaskManagement.TaskManagement.comparators.compareByStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class TaskService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TaskRepo taskRepo;

    @Autowired
    private AdminRepo adminRepo;
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
    public List<getAllTaskResp> getAllTaskForUser(String email) throws Exception
    {
        Optional<User> optionalUser=userRepo.findByEmail(email);
        if(optionalUser.isEmpty())
        {
            throw new UserNotFoundException("USER  NOT  EXIST");
        }
        User user =optionalUser.get();
        List<Task> taskList=user.getTaskList();
        List<getAllTaskResp> taskAndDueDatesList=new ArrayList<>();
        System.out.println(taskAndDueDatesList.size()+" size");
        for(Task t:taskList)
        {
            getAllTaskResp g=new getAllTaskResp();
            g.setTitle(t.getTitle());
            g.setDueDate(t.getDueDate());
            g.setStatus(t.getStatus());
            g.setTaskId(t.getTaskId());
            g.setUserId(t.getUser().getId());
            taskAndDueDatesList.add(g);
        }
        return taskAndDueDatesList;
    }

    public updateTaskResp updateTask (updateTaskRqst updatedTask) throws Exception
    {
        boolean isAdmin=false;
        if(updatedTask.getRol().equals("ADMIN"))
        {
            isAdmin=true;
        }
        Optional<Admin>optionalAdmin=adminRepo.findByEmail(updatedTask.getEmail());
        Optional<User>optionalUser=userRepo.findByEmail(updatedTask.getEmail());
        Optional<Task>optionalTask=taskRepo.findById(updatedTask.getId()); //this is task id
        if(isAdmin)
        {
            if(optionalAdmin.isEmpty())
            {
                throw new AdminNotExistException("ADMIN NOT FOUND");
            }
        }
        else {
            if(optionalUser.isEmpty())
            {
                throw new UserNotFoundException("USER NOT FOUND");
            }
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

    public  List<getAllTaskResp> getAllTasks()
    {
        List<Task>taskList=taskRepo.findAll();
        List<getAllTaskResp>allTaskList=new ArrayList<>();
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
        return allTaskList;
    }

    //sorting and  filtering for user
    public List<getAllTaskResp> sortBy(String email,boolean status, boolean date)throws  Exception
    {
        List<getAllTaskResp> taskList= null;
        try{
            taskList=getAllTaskForUser(email);
        }
        catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }
        if(status)
        {
            Collections.sort(taskList, new compareByStatus());
        } else if (date) {
            Collections.sort(taskList,new compareByDate());
        }
        return taskList;
    }

    public List<getAllTaskResp> filterBy(String email, Status status)throws  Exception
    {
        List<Task> taskList= taskRepo.findByUser_EmailAndStatus(email, status);
        List<getAllTaskResp>allTaskList=new ArrayList<>();
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
        return allTaskList;
   }

   //sorting  and filtering  for admin
    public List<getAllTaskResp> sortBy(boolean status, boolean date,boolean userId)
    {
        List<getAllTaskResp> taskList=getAllTasks();
        if(status)
        {
            Collections.sort(taskList, new compareByStatus());
        } else if (date) {
            Collections.sort(taskList,new compareByDate());
        }
        else if(userId)
        {
            Collections.sort(taskList,(a,b)-> {
                if(a.getUserId()<b.getUserId())
                {
                    return -1;
                }
                else if(a.getUserId()>b.getUserId())
                {
                    return 1;
                }
                else{
                    return 0;
                }
            });
        }
        return taskList;
    }

    public List<getAllTaskResp> filterBy( Status status, LocalDate dueDate) throws Exception
    {
        List<getAllTaskResp>allTask=null;
        if(status!=null && dueDate!=null)
        {
            List<Task>taskList=taskRepo.findByStatusAndDueDate(status,dueDate);
            for(Task t:taskList)
            {
                getAllTaskResp gt=new getAllTaskResp();
                gt.setTaskId(t.getTaskId());
                gt.setUserId(t.getUser().getId());
                gt.setTitle(t.getTitle());
                gt.setDueDate(t.getDueDate());
                gt.setStatus(t.getStatus());
                allTask.add(gt);
            }
            return allTask;
        }
        else if(status!=null && dueDate==null) {
            List<Task>taskList=taskRepo.findByStatus(status);
            for(Task t:taskList)
            {
                getAllTaskResp gt=new getAllTaskResp();
                gt.setTaskId(t.getTaskId());
                gt.setUserId(t.getUser().getId());
                gt.setTitle(t.getTitle());
                gt.setDueDate(t.getDueDate());
                gt.setStatus(t.getStatus());
                allTask.add(gt);
            }
            return allTask;
        }
        else if(status==null && dueDate!=null)
        {
            List<Task>taskList=taskRepo.findByDue(dueDate);
            for(Task t:taskList)
            {
                getAllTaskResp gt=new getAllTaskResp();
                gt.setTaskId(t.getTaskId());
                gt.setUserId(t.getUser().getId());
                gt.setTitle(t.getTitle());
                gt.setDueDate(t.getDueDate());
                gt.setStatus(t.getStatus());
                allTask.add(gt);
            }
            return allTask;
        }
        else{
            throw new NoFilteringParameterException("Please Select Filtering Parameter");
        }
    }





}










