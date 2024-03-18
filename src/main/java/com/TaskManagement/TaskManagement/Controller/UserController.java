package com.TaskManagement.TaskManagement.Controller;

import com.TaskManagement.TaskManagement.Enums.Status;
import com.TaskManagement.TaskManagement.Exceptions.UserNotFoundException;
import com.TaskManagement.TaskManagement.RequestDto.addTaskRqst;
import com.TaskManagement.TaskManagement.RequestDto.updateTaskRqst;
import com.TaskManagement.TaskManagement.ResponceDto.addTaskResp;
import com.TaskManagement.TaskManagement.ResponceDto.getAllTaskResp;
import com.TaskManagement.TaskManagement.ResponceDto.getTaskForUserResp;
import com.TaskManagement.TaskManagement.ResponceDto.updateTaskResp;
import com.TaskManagement.TaskManagement.Service.TaskService;
import com.TaskManagement.TaskManagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @PostMapping("/addTask")
    public ResponseEntity addTask(@RequestBody addTaskRqst taskAddingRqst)
    {
        try{
            addTaskResp addTaskResp= taskService.addTask(taskAddingRqst);
            return new ResponseEntity(addTaskResp,HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllTasksForUser/{userEmail}")
    public ResponseEntity getAllTaskForUser(@PathVariable String userEmail)
    {
        try{
            List<getAllTaskResp> taskAndDueDatesList =taskService.getAllTaskForUser(userEmail);
            if(taskAndDueDatesList.isEmpty())
            {
                return  new ResponseEntity("No Task Yet",HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(taskAndDueDatesList,HttpStatus.OK);
        }
        catch (UserNotFoundException e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.CONFLICT);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateTaskOrStatus")
    public ResponseEntity updateTaskOrStatus(@RequestBody updateTaskRqst updatedTask)
    {
        try{
            updateTaskResp task= taskService.updateTask(updatedTask);
            return new ResponseEntity(task,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteTask/{taskId}")
    public ResponseEntity deleteTaskById(@PathVariable String taskId)
    {
        try{
            String resp= taskService.deleteTaskById(taskId);
            return new ResponseEntity(resp,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sortBy/{userEmail}")
    public ResponseEntity sortBy(@PathVariable String  userEmail,
                                 @RequestParam("status")boolean status,
                                 @RequestParam("date")boolean date)
    {
        try{
            List<getAllTaskResp> taskList=taskService.sortBy(userEmail,status,date);
            return new ResponseEntity(taskList,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/filterBy/{userEmail}")
    public ResponseEntity filterBy(@PathVariable String  userEmail,
                                   @RequestParam("status")Status status)
    {
        try{
            List<getAllTaskResp> taskList=taskService.filterBy(userEmail,status);
            return new ResponseEntity(taskList,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
