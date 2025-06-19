package ua.glek.crm_adv.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.glek.crm_adv.dto.CompanyAnalyticsDTO;
import ua.glek.crm_adv.repository.AnalyticsRepo;

import java.util.List;

@Slf4j
@Service
public class AnalyticsService {
    @Autowired
    private AnalyticsRepo analyticsRepository;

    @Autowired
    private ClickHouseService clickHouseService;


    @Scheduled(cron = "0 0 2 * * *")
    public void syncToClickHouse() {
        log.info("Starting synchronization of company_analytics...");

        try {
            List<CompanyAnalyticsDTO> analytics = analyticsRepository.fetchCompanyAnalytics();

            if (analytics.isEmpty()) {
                log.warn("!No data found for synchronization.");
                return;
            }

            for (CompanyAnalyticsDTO dto : analytics) {
                clickHouseService.insertAnalytics(dto);
            }

            log.info("Successfully written {} records to ClickHouse", analytics.size());

        } catch (Exception e) {
            log.error("Error synchronizing with ClickHouse: {}", e.getMessage(), e);
        }
    }
}
