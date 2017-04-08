package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.MouvementStock;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MouvementStock entity.
 */
@SuppressWarnings("unused")
public interface MouvementStockRepository extends JpaRepository<MouvementStock,Long> {

}
