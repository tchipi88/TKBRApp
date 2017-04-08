package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.Compte;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Compte entity.
 */
@SuppressWarnings("unused")
public interface CompteRepository extends JpaRepository<Compte,Long> {

}
