/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.CompteAnalytiqueClient;
import com.itsolution.tkbr.domain.enumeration.CompteAnalytiqueClientType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the CompteAnalytiqueClient entity.
 */
public interface CompteAnalytiqueClientRepository extends JpaRepository<CompteAnalytiqueClient, Long> {

    public CompteAnalytiqueClient findByIntitule(String nom);

    public CompteAnalytiqueClient findByIntituleAndType(String nom, CompteAnalytiqueClientType compteAnalytiqueClientType);

    public Page<CompteAnalytiqueClient> findByType(Pageable pageable, CompteAnalytiqueClientType type);

    

}




