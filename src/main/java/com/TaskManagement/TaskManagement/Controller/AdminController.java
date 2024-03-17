package com.TaskManagement.TaskManagement.Controller;

import com.TaskManagement.TaskManagement.RequestDto.addAdminRqst;
import com.TaskManagement.TaskManagement.RequestDto.addUserRqst;
import com.TaskManagement.TaskManagement.ResponceDto.addAdminResp;
import com.TaskManagement.TaskManagement.ResponceDto.addUserResp;
import com.TaskManagement.TaskManagement.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping("/addAdmin")
    public ResponseEntity addAdmin(@RequestBody addAdminRqst adminAddingRqst)
    {
        try{
            addAdminResp resp= adminService.addAdmin(adminAddingRqst);
            return new ResponseEntity<>(resp,HttpStatus.CREATED);
         }
        catch (Exception e)
        {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
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

}
