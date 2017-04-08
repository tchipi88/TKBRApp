package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.EmployeFonction;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EmployeFonction entity.
 */
@SuppressWarnings("unused")
public interface EmployeFonctionRepository extends JpaRepository<EmployeFonction,Long> {

}
