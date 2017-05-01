/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.Access;
import com.itsolution.tkbr.domain.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Access entity.
 */
public interface AccessRepository extends JpaRepository<Access, Long> {
	
	 @Query("select  access from User user inner join  user.authorities authority "
	    		+ "inner join  authority.accessGroups accessGroups "
	    		+ "inner join  accessGroups.access access  where user.login =:login")
		List<Access> findOneByLoginWithEagerRelationships(@Param("login") String  login);
	 
	 @Query("select  access.valeur from User user inner join  user.authorities authority "
	    		+ "inner join  authority.accessGroups accessGroups "
	    		+ "inner join  accessGroups.access access  where user.login =:login")
		List<String> findOneByLoginWithEagerRelationships_String(@Param("login") String  login);

    

}




