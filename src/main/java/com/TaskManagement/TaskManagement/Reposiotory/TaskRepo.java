package com.TaskManagement.TaskManagement.Reposiotory;

import com.TaskManagement.TaskManagement.Enity.Task;
import com.TaskManagement.TaskManagement.Enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepo extends JpaRepository<Task,String> {
    List<Task> findByDue(LocalDate date);
    List<Task> findByStatus(Status status);

    List<Task> findByUser_Email(String email);

    List<Task> findByUser_EmailAndStatus(String email, Status status);

    List<Task> findByStatusAndDueDate(Status status, LocalDate dueDate);



}
