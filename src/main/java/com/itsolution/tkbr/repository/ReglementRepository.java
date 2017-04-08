package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.Reglement;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Reglement entity.
 */
@SuppressWarnings("unused")
public interface ReglementRepository extends JpaRepository<Reglement,Long> {

}
