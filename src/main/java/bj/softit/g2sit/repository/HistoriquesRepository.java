package bj.softit.g2sit.repository;

import bj.softit.g2sit.domain.Historiques;
import bj.softit.g2sit.domain.OutStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the Historiques entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HistoriquesRepository extends JpaRepository<Historiques,Long> {

    @Query("select historiques from Historiques historiques where historiques.user.login = ?#{principal.username}")
    List<Historiques> findByUserIsCurrentUser();

    Page<Historiques> findAllByOrderByDateDesc(Pageable pageable);

    List<Historiques> findAllByOrderByDateDesc();

//    Page<Historiques> findAllByDateBetween(Instant fromDate, Instant toDate, Pageable pageable);

    Page<Historiques> findAllByDateBetween(ZonedDateTime fromDate, ZonedDateTime toDate, Pageable pageable);
//    Page<Historiques> findAllByDateBetweenOrderByDateDesc(ZonedDateTime fromDate, ZonedDateTime toDate, Pageable pageable);
    Page<Historiques> findAllByDateBetweenAndStocks_Produit_IdOrOuts_Produit_Id(ZonedDateTime fromDate, ZonedDateTime toDate, long id,Long prodId, Pageable pageable);
    Page<Historiques> findAllByDateBetweenAndStocks_Produit_IdOrOuts_Produit_IdOrderByDateDesc(ZonedDateTime fromDate, ZonedDateTime toDate, long id,Long prodId, Pageable pageable);
    Page<Historiques> findAllByDateBetweenAndStocks_Produit_IdOrOuts_Produit_IdAndOperationNotLike(ZonedDateTime fromDate, ZonedDateTime toDate, long id,Long prodId,String op, Pageable pageable);
//    Page<Historiques> findAllByDateBetweenAndOuts_Produit_IdOrStocks_Produit_Id(ZonedDateTime fromDate, ZonedDateTime toDate, long id, Pageable pageable);

        long countAllByDateBetween(ZonedDateTime fromDate, ZonedDateTime toDate);
//        long countHistoriquesByDateBetweenAndStocksIsNotNull(ZonedDateTime fromDate, ZonedDateTime toDate);
}
