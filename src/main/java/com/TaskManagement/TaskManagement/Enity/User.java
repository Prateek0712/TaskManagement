package com.TaskManagement.TaskManagement.Enity;

import com.TaskManagement.TaskManagement.Enums.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder


/*
  THIS INCLUDE ADMIN AS WELL AS NORMAL USER  ONLY WE ASSIGN  ROLES ADMIN OR USER TO THEM VIA ENUM
 */
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  Id;

    @Column(unique = true)
    private String userName;
    private String password;

    @Column(unique = true)
    private String email;
    @Enumerated(value=EnumType.STRING)
    private Roles rol;

    // connecting to Tasks bi-directionlly as a Parent

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Task> taskList=new ArrayList<>();
}
