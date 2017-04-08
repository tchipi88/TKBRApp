package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.CommandeLigne;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CommandeLigne entity.
 */
@SuppressWarnings("unused")
public interface CommandeLigneRepository extends JpaRepository<CommandeLigne,Long> {

}
