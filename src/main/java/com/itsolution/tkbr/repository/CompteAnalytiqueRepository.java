/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.CompteAnalytique;
import com.itsolution.tkbr.domain.enumeration.CompteAnalytiqueType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the CompteAnalytique entity.
 */
public interface CompteAnalytiqueRepository extends JpaRepository<CompteAnalytique, Long> {

    public CompteAnalytique findByIntitule(String nom);

    public CompteAnalytique findByIntituleAndType(String nom, CompteAnalytiqueType type);

    public Page<CompteAnalytique> findByType(Pageable pageable, CompteAnalytiqueType type);

}
