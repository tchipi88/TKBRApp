/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Compte entity.
 */
public interface CompteRepository extends JpaRepository<Compte, Integer> {

    

}




