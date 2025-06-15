package ua.glek.crm_adv.restController;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.glek.crm_adv.service.ClickHouseService;

@RestController
@RequestMapping("/api/clickhouse")
@AllArgsConstructor
public class ClickHouseController {
    private final ClickHouseService clickHouseService;

    @GetMapping("/version")
    public ResponseEntity<String> getVersion() {
        return ResponseEntity.ok("Version: " + clickHouseService.getVersion());
    }
}
