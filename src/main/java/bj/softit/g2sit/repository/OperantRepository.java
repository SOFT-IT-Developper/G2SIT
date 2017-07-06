package bj.softit.g2sit.repository;

import bj.softit.g2sit.domain.Operant;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Operant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OperantRepository extends JpaRepository<Operant,Long> {
    
}
