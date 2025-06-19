package ua.glek.crm_adv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.glek.crm_adv.dto.CompanyAnalyticsDTO;

import java.util.List;

@Service
public class ClickHouseService {
    @Autowired
    @Qualifier("clickHouseJdbcTemplate")
    private JdbcTemplate clickHouseJdbcTemplate;

    public void insertAnalytics(CompanyAnalyticsDTO dto){
        String sql = """
            INSERT INTO company_analytics (
              company_id, num_products, avg_price, total_orders,
              avg_order_price, completion_rate, days_since_last_order, is_successful
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

       clickHouseJdbcTemplate.update(sql,
               dto.getCompanyId(),
               dto.getNumProducts(),
               dto.getAvgPrice(),
               dto.getTotalOrders(),
               dto.getAvgOrderPrice(),
               dto.getCompletionRate(),
               dto.getDaysSinceLastOrder(),
               dto.getIsSuccessful()
       );

    }

    public void insertAnalyticsBatch(List<CompanyAnalyticsDTO> list) {
        String sql = """
            INSERT INTO company_analytics (
              company_id, num_products, avg_price, total_orders,
              avg_order_price, completion_rate, days_since_last_order, is_successful
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        List<Object[]> batchArgs = list.stream().map(dto -> new Object[] {
                dto.getCompanyId(),
                dto.getNumProducts(),
                dto.getAvgPrice(),
                dto.getTotalOrders(),
                dto.getAvgOrderPrice(),
                dto.getCompletionRate(),
                dto.getDaysSinceLastOrder(),
                dto.getIsSuccessful()
        }).toList();

        clickHouseJdbcTemplate.batchUpdate(sql, batchArgs);
    }


}
