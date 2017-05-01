/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.AccessGroup;
import com.itsolution.tkbr.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
	
	 @Query("select authority from Authority authority left join fetch authority.accessGroups where authority.name =:name")
	 Authority findOneWithEagerRelationships(@Param("name") String  name);

    

}




