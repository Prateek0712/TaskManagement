package com.TaskManagement.TaskManagement.Enity;

import com.TaskManagement.TaskManagement.Enums.Roles;
import com.TaskManagement.TaskManagement.Enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Admins")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;
    private String adminName;
    private String email;
    private String  password;

    @Enumerated(value = EnumType.STRING)
    private Roles rol;

}
