package ua.glek.crm_adv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private Double price;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @ManyToMany(mappedBy = "products")
    @JsonIgnoreProperties({"products"})
    private List<Order> orders = new ArrayList<>();
}
