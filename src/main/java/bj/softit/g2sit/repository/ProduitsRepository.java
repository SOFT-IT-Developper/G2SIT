package bj.softit.g2sit.repository;

import bj.softit.g2sit.domain.Produits;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Produits entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduitsRepository extends JpaRepository<Produits,Long> {
    @Override
    long count();
    Produits findByNameProduit(String name);
   List<Produits>  findAllByNameProduitOrDescription(String name, String description);
}
