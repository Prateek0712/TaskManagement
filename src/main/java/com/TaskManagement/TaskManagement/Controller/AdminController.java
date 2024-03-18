package com.TaskManagement.TaskManagement.Controller;

import com.TaskManagement.TaskManagement.Enums.Status;
import com.TaskManagement.TaskManagement.RequestDto.addAdminRqst;
import com.TaskManagement.TaskManagement.RequestDto.addUserRqst;
import com.TaskManagement.TaskManagement.ResponceDto.addAdminResp;
import com.TaskManagement.TaskManagement.ResponceDto.addUserResp;
import com.TaskManagement.TaskManagement.ResponceDto.getAllTaskResp;
import com.TaskManagement.TaskManagement.Service.AdminService;
import com.TaskManagement.TaskManagement.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private AdminService adminService;
    @GetMapping("/getAllTask")
    public ResponseEntity getAllTask()
    {
        List<getAllTaskResp> allTaskList=taskService.getAllTasks();
        if(allTaskList.size()==0)
        {
            return new ResponseEntity("No Tasks Yet",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(allTaskList,HttpStatus.FOUND);
    }
    @DeleteMapping("/deleteUser/{email}")
    public ResponseEntity deleteUser(@PathVariable String email)
    {
        try{
            String resp= adminService.deleteUser(email);
            return new ResponseEntity(resp,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sortBy")
    public ResponseEntity sortBy(@RequestParam("status")boolean status,
                                 @RequestParam("date")boolean date,
                                 @RequestParam("userId")boolean userId)
    {
        try{
            List<getAllTaskResp> taskList=taskService.sortBy(status,date,userId);
            return new ResponseEntity(taskList,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/filterBy")
    public ResponseEntity filterBy(@RequestParam("status") Status status,
                                   @RequestParam("date")LocalDate date)
    {
        try{
            List<getAllTaskResp> taskList=taskService.filterBy(status,date);
            return new ResponseEntity(taskList,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
