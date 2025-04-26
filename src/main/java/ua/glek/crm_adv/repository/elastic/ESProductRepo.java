package ua.glek.crm_adv.repository.elastic;

import io.lettuce.core.support.caching.CacheAccessor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ua.glek.crm_adv.model.elastic.ESProduct;
import ua.glek.crm_adv.model.jpa.Category;

import java.util.List;

@Repository
public interface ESProductRepo extends ElasticsearchRepository<ESProduct, Long> {
    List<ESProduct> findByNameContaining(String name);

    List<ESProduct> findByCategory(Category category);

    Iterable<ESProduct> findByCategoryName(String categoryName);
}
