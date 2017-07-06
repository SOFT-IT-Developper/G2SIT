package bj.softit.g2sit.repository.search;

import bj.softit.g2sit.domain.Operant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Operant entity.
 */
public interface OperantSearchRepository extends ElasticsearchRepository<Operant, Long> {
}
