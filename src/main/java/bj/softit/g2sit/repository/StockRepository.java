package bj.softit.g2sit.repository;

import bj.softit.g2sit.domain.Stock;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;


/**
 * Spring Data JPA repository for the Stock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockRepository extends JpaRepository<Stock,Long> {

    Stock findByProduit_Id(Long id);
    List<Stock> findAllByProduit_Id(long id);
    List<Stock> findByDateentrerBetween(ZonedDateTime d1, ZonedDateTime d2);
    long countAllByQuantiteIsAfter(BigDecimal q);
    long countAllByQuantiteIsBefore(BigDecimal q);
}
