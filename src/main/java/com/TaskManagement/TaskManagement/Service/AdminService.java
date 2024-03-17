package com.TaskManagement.TaskManagement.Service;

import com.TaskManagement.TaskManagement.Enity.Admin;
import com.TaskManagement.TaskManagement.Enity.User;
import com.TaskManagement.TaskManagement.Exceptions.AdminAlreadyExistException;
import com.TaskManagement.TaskManagement.Exceptions.UserNotFoundException;
import com.TaskManagement.TaskManagement.Reposiotory.AdminRepo;
import com.TaskManagement.TaskManagement.Reposiotory.UserRepo;
import com.TaskManagement.TaskManagement.RequestDto.addAdminRqst;
import com.TaskManagement.TaskManagement.ResponceDto.addAdminResp;
import com.TaskManagement.TaskManagement.Transformers.AdminTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private UserRepo userRepo;
    public addAdminResp addAdmin(addAdminRqst adminRqst) throws Exception
    {
        Optional<Admin> optionalAdmin = adminRepo.findByEmail(adminRqst.getEmail());
        if(optionalAdmin.isPresent())
        {
            throw new AdminAlreadyExistException("Admin Already Exist  Please  Login");
        }
        Admin admin= AdminTransformer.addAdminRqstToAdmin(adminRqst);
        admin=adminRepo.save(admin);
        addAdminResp ad=new addAdminResp();
        ad.setId(admin.getAdminId());
        ad.setEmail(admin.getEmail());
        ad.setUserName(admin.getAdminName());
        ad.setRol(admin.getRol());
        return ad;
    }
    public String deleteUser(String email)throws Exception
    {
        Optional<User> optionalUser=userRepo.findByEmail(email);
        if(optionalUser.isEmpty())
        {
            throw new UserNotFoundException("User You Wanted To Delete Is Not Exist");
        }
        User user=optionalUser.get();
        userRepo.delete(user);
        return "USER WITH EMAIL: "+user.getEmail()+" IS DELETED SUCCESSFULLY";
    }
}
