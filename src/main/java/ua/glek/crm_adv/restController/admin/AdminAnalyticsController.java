package ua.glek.crm_adv.restController.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.glek.crm_adv.service.AnalyticsService;

@RestController
@RequestMapping("/api/admin")
public class AdminAnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;
    @PostMapping("/syncToClickhouse")
    public ResponseEntity<String> sync(){
        analyticsService.syncToClickHouse();
        return ResponseEntity.ok("Sync started");
    }
}
