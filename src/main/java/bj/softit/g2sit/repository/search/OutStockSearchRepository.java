package bj.softit.g2sit.repository.search;

import bj.softit.g2sit.domain.OutStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.Instant;

/**
 * Spring Data Elasticsearch repository for the OutStock entity.
 */
public interface OutStockSearchRepository extends ElasticsearchRepository<OutStock, Long> {
   Page<OutStock> findAllByDatesortirBetween(Instant fromDate, Instant toDate, Pageable pageable);
}
