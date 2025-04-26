package ua.glek.crm_adv.request;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BanRequest {
    private LocalDateTime untilTime;
}
