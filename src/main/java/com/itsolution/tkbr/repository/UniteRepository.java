package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.Unite;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Unite entity.
 */
@SuppressWarnings("unused")
public interface UniteRepository extends JpaRepository<Unite,Long> {

}
