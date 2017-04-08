package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.ProduitFournisseur;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProduitFournisseur entity.
 */
@SuppressWarnings("unused")
public interface ProduitFournisseurRepository extends JpaRepository<ProduitFournisseur,Long> {

}
