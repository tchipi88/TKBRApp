/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.EcritureCompteAnalytique;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
public interface EcritureCompteAnalytiqueRepository extends JpaRepository<EcritureCompteAnalytique, Long> {

    public Page<EcritureCompteAnalytique> findAllByDateEcritureBetween(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);
    
}
