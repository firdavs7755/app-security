package com.example.appsecurity.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReqUser {
    private String fill_name;
    private String username;
    private String password;
    private String email;
}
