package ua.glek.crm_adv.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.glek.crm_adv.dto.CompanyAnalyticsDTO;

import java.util.List;

@Repository
public class AnalyticsRepo {

    @Autowired
   @Qualifier("postgresJdbcTemplate")
    private JdbcTemplate postgesjdbcTemplate;


    public List<CompanyAnalyticsDTO> fetchCompanyAnalytics() {
        String sql = """
           SELECT
               c.id AS company_id,
               COUNT(DISTINCT cp.products_id) AS num_products,
               AVG(p.price) AS avg_price,
               COUNT(DISTINCT o.id) AS total_orders,
               AVG(o.total_price) AS avg_order_price,
               SUM(CASE WHEN o.status = 'COMPLETED' THEN 1 ELSE 0 END)::FLOAT / NULLIF(COUNT(o.id), 0) AS completion_rate,
               DATE_PART('day', CURRENT_DATE - MAX(o.order_date)) AS days_since_last_order,
               CASE
                   WHEN SUM(o.total_price) > 100 OR
                        (SUM(CASE WHEN o.status = 'COMPLETED' THEN 1 ELSE 0 END)::FLOAT / NULLIF(COUNT(o.id), 0)) > 0.6
                   THEN 1 ELSE 0
               END AS is_successful
           FROM company c
           JOIN company_products cp ON cp.company_id = c.id
           JOIN product p ON p.id = cp.products_id
           JOIN order_products op ON op.product_id = p.id
           JOIN orders o ON o.id = op.order_id
           GROUP BY c.id;
        """;

        return postgesjdbcTemplate.query(sql, (rs, rowNum) -> new CompanyAnalyticsDTO(
                rs.getLong("company_id"),
                rs.getInt("num_products"),
                rs.getDouble("avg_price"),
                rs.getInt("total_orders"),
                rs.getDouble("avg_order_price"),
                rs.getDouble("completion_rate"),
                rs.getInt("days_since_last_order"),
                rs.getInt("is_successful")
        ));
    }
}
