package ua.glek.crm_adv.model.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JsonIgnoreProperties({"category"})
    private Product product;
    @ManyToOne
    @JsonIgnore
    private Order order;

    private int quantity;


    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }




}
