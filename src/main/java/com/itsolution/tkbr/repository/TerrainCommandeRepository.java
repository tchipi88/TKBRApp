/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.TerrainCommande;
import com.itsolution.tkbr.domain.enumeration.TypeCommande;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the TerrainCommande entity.
 */
public interface TerrainCommandeRepository extends JpaRepository<TerrainCommande, Long> {

    public Page<TerrainCommande> findByType(Pageable pageable, TypeCommande type);

    public Page<TerrainCommande> findAllByTypeAndDateEmissionBetween(TypeCommande type, LocalDate fromDate, LocalDate toDate, Pageable pageable);

    

}




