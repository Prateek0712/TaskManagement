package com.TaskManagement.TaskManagement.ResponceDto;

import com.TaskManagement.TaskManagement.Enums.Roles;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class addAdminResp {
    private Long  id;
    private String email;
    private String  userName;
    private Roles rol;
}
