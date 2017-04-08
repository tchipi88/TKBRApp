package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.Employe;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Employe entity.
 */
@SuppressWarnings("unused")
public interface EmployeRepository extends JpaRepository<Employe,Long> {

}
