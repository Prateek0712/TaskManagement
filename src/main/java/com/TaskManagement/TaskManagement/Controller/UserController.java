package com.TaskManagement.TaskManagement.Controller;

import com.TaskManagement.TaskManagement.Enums.Status;
import com.TaskManagement.TaskManagement.RequestDto.addTaskRqst;
import com.TaskManagement.TaskManagement.RequestDto.sortAndFilterRqst;
import com.TaskManagement.TaskManagement.RequestDto.updateTaskRqst;
import com.TaskManagement.TaskManagement.ResponceDto.addTaskResp;
import com.TaskManagement.TaskManagement.ResponceDto.getAllTaskResp;
import com.TaskManagement.TaskManagement.ResponceDto.updateTaskResp;
import com.TaskManagement.TaskManagement.Service.TaskService;
import com.TaskManagement.TaskManagement.Service.UserService;
import com.TaskManagement.TaskManagement.securityCongifuration.JwtService;
import com.TaskManagement.TaskManagement.securityCongifuration.JwtTokenExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private TaskService taskService;

    @PostMapping("/addTask")
    public ResponseEntity addTask(@RequestBody addTaskRqst taskAddingRqst,HttpServletRequest request)
    {
        try{
            String jwtToken = JwtTokenExtractor.extractToken(request);
            String email=jwtService.extractUsername(jwtToken);
            addTaskResp addTaskResp= taskService.addTask(taskAddingRqst,email);
            return new ResponseEntity(addTaskResp,HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllTask")
    public ResponseEntity getAllTask(HttpServletRequest request)
    {
        try{
            String jwtToken = JwtTokenExtractor.extractToken(request);
            String email=jwtService.extractUsername(jwtToken);
            List<getAllTaskResp> taskAndDueDatesList =taskService.getAllTasks(email);
            if(taskAndDueDatesList.isEmpty())
            {
                return  new ResponseEntity("No Task Yet",HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(taskAndDueDatesList,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/sortAndFilter")
    public ResponseEntity sortAndFilter(@RequestBody sortAndFilterRqst rqst,
                                        HttpServletRequest request)
    {
        String jwtToken = JwtTokenExtractor.extractToken(request);
        String email=jwtService.extractUsername(jwtToken);
        List<getAllTaskResp> taskList= taskService.sortAndFilter(rqst,email);
        if(taskList.size()==0)
        {
            return new ResponseEntity("Empty......!!",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(taskList,HttpStatus.OK);

    }
    @PutMapping("/updateTaskOrStatus")
    public ResponseEntity updateTaskOrStatus(@RequestBody updateTaskRqst updatedTask,HttpServletRequest request)
    {
        try{
            String jwtToken = JwtTokenExtractor.extractToken(request);
            String email=jwtService.extractUsername(jwtToken);
            updateTaskResp task=taskService.updateTask(updatedTask,email);
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


    @GetMapping("/curr-user")
    public String getCurrUser(HttpServletRequest request) {

        String jwtToken = JwtTokenExtractor.extractToken(request);
        if(jwtToken!=null)
        {
            return jwtService.extractUsername(jwtToken)+" ----> this is username";
        }
        return null;
    }
}
