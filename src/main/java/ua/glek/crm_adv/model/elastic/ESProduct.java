package ua.glek.crm_adv.model.elastic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import ua.glek.crm_adv.model.jpa.Category;
import ua.glek.crm_adv.model.jpa.User;

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
    @Field(type = FieldType.Object)
    private Category category;

}
