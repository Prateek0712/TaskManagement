package com.TaskManagement.TaskManagement.RequestDto;

import com.TaskManagement.TaskManagement.Enums.Roles;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class addUserRqst {
    private String email;
    private String userName;
    private String password;
}
