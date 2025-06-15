package ua.glek.crm_adv.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClickHouseService {
    private final JdbcTemplate jdbcTemplate;

    public ClickHouseService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getVersion(){
        return jdbcTemplate.queryForObject("SELECT version()", String.class);
    }

    public void InsertOrder(Long orderId,Long userId,String productName,double amount, LocalDateTime date){
        String sql = "INSERT INTO orders VALUES(order_id,user_id,product_name,amount,date)";
        jdbcTemplate.update(sql,orderId,userId,productName,amount,date);
    }
}
