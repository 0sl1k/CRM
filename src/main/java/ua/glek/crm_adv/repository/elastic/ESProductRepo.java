package ua.glek.crm_adv.repository.elastic;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ua.glek.crm_adv.model.elastic.ESProduct;

import java.util.List;

@Repository
public interface ESProductRepo extends ElasticsearchRepository<ESProduct, Long> {
    List<ESProduct> findByNameContaining(String name);
}
