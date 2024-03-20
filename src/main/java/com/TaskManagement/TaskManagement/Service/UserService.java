package com.TaskManagement.TaskManagement.Service;

import com.TaskManagement.TaskManagement.Enity.User;
import com.TaskManagement.TaskManagement.Exceptions.UserAlreadyExistException;
import com.TaskManagement.TaskManagement.Exceptions.UserNotFoundException;
import com.TaskManagement.TaskManagement.Reposiotory.UserRepo;
import com.TaskManagement.TaskManagement.RequestDto.addUserRqst;
import com.TaskManagement.TaskManagement.ResponceDto.addUserResp;
import com.TaskManagement.TaskManagement.Transformers.UserTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder encoder;
    public addUserResp addUser(addUserRqst userAddingRqst) throws  Exception
    {
        Optional<User> optionalUser=userRepo.findByEmail(userAddingRqst.getEmail());
        if(optionalUser.isPresent())
        {
            throw new UserAlreadyExistException("This User Is Already Exist In System");
        }
        User user= UserTransformers.addUserRqstToUser(userAddingRqst);
        user.setPassword(encoder.encode(userAddingRqst.getPassword()));
        user =userRepo.save(user);
        addUserResp addUserResp=new addUserResp();
        addUserResp.setId(user.getId());
        addUserResp.setUserName(user.getUserName());
        addUserResp.setEmail(user.getEmail());
        addUserResp.setRol(user.getRol());
        return addUserResp;
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
    public List<String> getAllUsers()
    {
        List<User> userList=userRepo.findAll();
        List<String> ans=new ArrayList<>();
        for(User user:userList)
        {
            String str= user.getUserName()+" "+user.getId()+" "+user.getEmail()+" "+user.getRol();
            ans.add(str);
        }
        System.out.println(ans.size());
        return ans;
    }

}
