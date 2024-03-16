package com.TaskManagement.TaskManagement.Exceptions;

public class UserAlreadyExistException extends Exception{
    public UserAlreadyExistException(String msg)
    {
        super(msg);
    }
}
