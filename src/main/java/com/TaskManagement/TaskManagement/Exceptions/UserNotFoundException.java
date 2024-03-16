package com.TaskManagement.TaskManagement.Exceptions;

import com.TaskManagement.TaskManagement.Enity.User;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String msg)
    {
        super(msg);
    }

}
