package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.Entrepot;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Entrepot entity.
 */
@SuppressWarnings("unused")
public interface EntrepotRepository extends JpaRepository<Entrepot,Long> {

}
