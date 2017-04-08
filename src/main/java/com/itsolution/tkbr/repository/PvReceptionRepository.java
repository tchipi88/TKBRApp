package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.PvReception;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PvReception entity.
 */
@SuppressWarnings("unused")
public interface PvReceptionRepository extends JpaRepository<PvReception,Long> {

}
