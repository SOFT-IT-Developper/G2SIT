package bj.softit.g2sit.repository;

import bj.softit.g2sit.domain.OutStock;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OutStock entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OutStockRepository extends JpaRepository<OutStock,Long> {
    
}
