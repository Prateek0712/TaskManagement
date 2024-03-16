package com.TaskManagement.TaskManagement.Enity;

import com.TaskManagement.TaskManagement.Enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  String taskId;
    private  String title;
    private  String description;
    private LocalDate dueDate;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    //  connecting to user bi-directionally as child

    @JoinColumn
    @ManyToOne
    private User user;

}
