package bj.softit.g2sit.repository.search;

import bj.softit.g2sit.domain.Historiques;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Historiques entity.
 */
public interface HistoriquesSearchRepository extends ElasticsearchRepository<Historiques, Long> {
}
