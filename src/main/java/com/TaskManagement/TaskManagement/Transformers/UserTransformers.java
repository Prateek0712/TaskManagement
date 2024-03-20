package com.TaskManagement.TaskManagement.Transformers;

import com.TaskManagement.TaskManagement.Enity.User;
import com.TaskManagement.TaskManagement.RequestDto.addUserRqst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserTransformers {
    public static User addUserRqstToUser(addUserRqst userAddingRqst)
    {
        User user= User.builder()
                .userName(userAddingRqst.getUserName())
                .email(userAddingRqst.getEmail())
                .password(userAddingRqst.getPassword())
                .rol(userAddingRqst.getRol())
                .build();
        return user;
    }
}
