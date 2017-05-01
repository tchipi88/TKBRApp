/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.AccessGroup;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the AccessGroup entity.
 */
public interface AccessGroupRepository extends JpaRepository<AccessGroup, Long> {
	
	 @Query("select groupe from AccessGroup groupe left join fetch groupe.access where groupe.id =:id")
	 AccessGroup findOneWithEagerRelationships(@Param("id") Long id);

    

}




