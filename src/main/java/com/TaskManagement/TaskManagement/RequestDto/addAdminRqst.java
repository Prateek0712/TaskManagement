package com.TaskManagement.TaskManagement.RequestDto;

import com.TaskManagement.TaskManagement.Enums.Roles;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class addAdminRqst {
    private String email;
    private String adminName;
    private String password;
    private Roles rol;
}
