package bj.softit.g2sit.repository.search;

import bj.softit.g2sit.domain.Stock;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Stock entity.
 */
public interface StockSearchRepository extends ElasticsearchRepository<Stock, Long> {
}
