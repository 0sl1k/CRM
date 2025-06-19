package ua.glek.crm_adv.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CompanyAnalyticsDTO {
    private Long companyId;
    private Integer numProducts;
    private Double avgPrice;
    private Integer totalOrders;
    private Double avgOrderPrice;
    private Double completionRate;
    private Integer daysSinceLastOrder;
    private Integer isSuccessful;
}
