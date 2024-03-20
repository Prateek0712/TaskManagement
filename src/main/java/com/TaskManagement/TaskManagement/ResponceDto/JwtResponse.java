package com.TaskManagement.TaskManagement.ResponceDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class JwtResponse {
    private String jwtToken;
    private String email;
}
