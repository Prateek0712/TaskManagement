package com.TaskManagement.TaskManagement.Reposiotory;

import com.TaskManagement.TaskManagement.Enity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepo extends JpaRepository<Task,String> {
}
