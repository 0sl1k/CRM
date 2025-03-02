package ua.glek.crm_adv.dto;

import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String email;
    private String username;
    private String password;
    private Set<String> role;
}
