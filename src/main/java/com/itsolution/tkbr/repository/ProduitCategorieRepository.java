package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.ProduitCategorie;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProduitCategorie entity.
 */
@SuppressWarnings("unused")
public interface ProduitCategorieRepository extends JpaRepository<ProduitCategorie,Long> {

}
