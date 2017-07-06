package bj.softit.g2sit.repository;

import bj.softit.g2sit.domain.Historiques;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Historiques entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoriquesRepository extends JpaRepository<Historiques,Long> {
    
}
