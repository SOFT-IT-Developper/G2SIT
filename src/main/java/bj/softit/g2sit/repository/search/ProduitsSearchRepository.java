package bj.softit.g2sit.repository.search;

import bj.softit.g2sit.domain.Produits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Produits entity.
 */
public interface ProduitsSearchRepository extends ElasticsearchRepository<Produits, Long> {
}
