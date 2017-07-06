package bj.softit.g2sit.repository;

import bj.softit.g2sit.domain.Historiques;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Historiques entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoriquesRepository extends JpaRepository<Historiques,Long> {

    @Query("select historiques from Historiques historiques where historiques.user.login = ?#{principal.username}")
    List<Historiques> findByUserIsCurrentUser();
    List<Historiques> findAllByOrderByDateDesc();
    Page<Historiques> findAllByOrderByDateDesc(Pageable p);

}
