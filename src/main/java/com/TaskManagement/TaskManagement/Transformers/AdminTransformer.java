package com.TaskManagement.TaskManagement.Transformers;

import com.TaskManagement.TaskManagement.Enity.Admin;
import com.TaskManagement.TaskManagement.Enity.User;
import com.TaskManagement.TaskManagement.RequestDto.addAdminRqst;
import com.TaskManagement.TaskManagement.RequestDto.addUserRqst;

public class AdminTransformer {
    public static Admin addAdminRqstToAdmin(addAdminRqst adminAddingRqst)
    {
        Admin admin= Admin.builder()
                .adminName(adminAddingRqst.getAdminName())
                .email(adminAddingRqst.getEmail())
                .password(adminAddingRqst.getPassword())
                .rol(adminAddingRqst.getRol())
                .build();
        return admin;
    }
}
