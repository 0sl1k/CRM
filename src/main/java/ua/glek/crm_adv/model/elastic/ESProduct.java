package ua.glek.crm_adv.model.elastic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ESProduct {
    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
    private String category;
}
