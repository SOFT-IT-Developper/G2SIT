package bj.softit.g2sit.repository.search;

import bj.softit.g2sit.domain.Historiques;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.Instant;

/**
 * Spring Data Elasticsearch repository for the Historiques entity.
 */
public interface HistoriquesSearchRepository extends ElasticsearchRepository<Historiques, Long> {
    Page<Historiques> findAllByDateBetween(Instant fromDate, Instant toDate, Pageable pageable);
}
