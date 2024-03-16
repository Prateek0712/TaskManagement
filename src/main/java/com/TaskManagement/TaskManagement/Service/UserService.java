package com.TaskManagement.TaskManagement.Service;

import com.TaskManagement.TaskManagement.Enity.User;
import com.TaskManagement.TaskManagement.Exceptions.UserAlreadyExistException;
import com.TaskManagement.TaskManagement.Reposiotory.UserRepo;
import com.TaskManagement.TaskManagement.RequestDto.addUserRqst;
import com.TaskManagement.TaskManagement.ResponceDto.addUserResp;
import com.TaskManagement.TaskManagement.Transformers.UserTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    public addUserResp addUser(addUserRqst userAddingRqst) throws  Exception
    {
        Optional<User> optionalUser=userRepo.findByEmail(userAddingRqst.getEmail());
        if(optionalUser.isPresent())
        {
            throw new UserAlreadyExistException("This User Is Already Exist In System");
        }
        User user= UserTransformers.addUserRqstToUser(userAddingRqst);
        user =userRepo.save(user);
        addUserResp addUserResp=new addUserResp();
        addUserResp.setId(user.getId());
        addUserResp.setUserName(user.getUserName());
        addUserResp.setEmail(user.getEmail());
        addUserResp.setRol(user.getRol());
        return addUserResp;
    }

}
