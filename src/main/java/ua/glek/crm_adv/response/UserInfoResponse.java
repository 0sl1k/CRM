package ua.glek.crm_adv.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;



}