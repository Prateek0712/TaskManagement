package com.TaskManagement.TaskManagement.Controller;

import com.TaskManagement.TaskManagement.RequestDto.addUserRqst;
import com.TaskManagement.TaskManagement.ResponceDto.addUserResp;
import com.TaskManagement.TaskManagement.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private UserService userService;
    @PostMapping("/addAdmin")
    public ResponseEntity addAdmin(@RequestBody addUserRqst userAddingRqst)
    {
        addUserResp addUserResp= null;
        try{
            addUserResp=userService.addUser(userAddingRqst);
            return new ResponseEntity(addUserResp,HttpStatus.CREATED);}
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/addUser")
    public ResponseEntity addUser(@RequestBody addUserRqst userAddingRqst)
    {
        addUserResp addUserResp= null;
        try{
            addUserResp=userService.addUser(userAddingRqst);
            return new ResponseEntity(addUserResp,HttpStatus.CREATED);}
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
