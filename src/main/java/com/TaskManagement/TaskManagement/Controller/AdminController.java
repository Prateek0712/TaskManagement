package com.TaskManagement.TaskManagement.Controller;

import com.TaskManagement.TaskManagement.Enums.Status;
import com.TaskManagement.TaskManagement.ResponceDto.getAllTaskResp;
import com.TaskManagement.TaskManagement.Service.TaskService;
import com.TaskManagement.TaskManagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;


    @GetMapping("/getAllUsers")
    public ResponseEntity getAllUsers()
    {
        List<String>resp=userService.getAllUsers();
        if(resp.size()==0)
        {
            return new ResponseEntity("Empty.....!",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(resp,HttpStatus.OK);
    }
    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity deleteUser(@PathVariable String email)
    {
        try{
            String resp= userService.deleteUser(email);
            return new ResponseEntity(resp,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


}
