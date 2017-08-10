package bj.softit.g2sit.repository;

import bj.softit.g2sit.repository.search.StockSearchRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
@SuppressWarnings("unused")
@Repository
public interface StockRespersitoryExtend extends StockRepository {
    long countAllByDateentrerBetween(ZonedDateTime fromDate, ZonedDateTime toDate);
}
