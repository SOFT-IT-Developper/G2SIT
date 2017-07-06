package bj.softit.g2sit.repository.search;

import bj.softit.g2sit.domain.OutStock;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the OutStock entity.
 */
public interface OutStockSearchRepository extends ElasticsearchRepository<OutStock, Long> {
}
