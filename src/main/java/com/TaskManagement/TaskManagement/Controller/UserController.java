package com.TaskManagement.TaskManagement.Controller;

import com.TaskManagement.TaskManagement.RequestDto.addTaskRqst;
import com.TaskManagement.TaskManagement.RequestDto.addUserRqst;
import com.TaskManagement.TaskManagement.ResponceDto.addTaskResp;
import com.TaskManagement.TaskManagement.ResponceDto.addUserResp;
import com.TaskManagement.TaskManagement.Service.TaskService;
import com.TaskManagement.TaskManagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;
    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestBody addUserRqst userAddinngRqst)
    {
        addUserResp addUserResp= null;
        try{
            addUserResp=userService.addUser(userAddinngRqst);
            return new ResponseEntity(addUserResp,HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

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

}
