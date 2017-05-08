/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.Paie;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Paie entity.
 */
public interface PaieRepository extends JpaRepository<Paie, Long> {

    public Page<Paie> findAllByDateVersementBetween(LocalDate fromDate, LocalDate toDate, Pageable pageable);

    

}




